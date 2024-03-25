package io.keycloak.keycloaklogout.controller

import io.jsonwebtoken.Jwts
import io.keycloak.keycloaklogout.dto.KeycloakLogoutToken
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.*


@RestController
class LogoutController(private val redisTemplate: RedisTemplate<String, String>) {

    @PostMapping("/sso-logout", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun backchannelLogout(@RequestParam("logout_token") logoutToken: String) {

        println(logoutToken)

        val keycloakLogoutToken = parseKeycloakLogoutToken(logoutToken)
        print(keycloakLogoutToken)

        redisTemplate.opsForHash<String, String>().delete("spring:session:sessions", keycloakLogoutToken.sessionId);
        //todo:: redis session 제거!

    }

    fun parseKeycloakLogoutToken(token: String): KeycloakLogoutToken {
        // Decode the public key from Base64
        val key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsUW5qAsGITtZuWghpDanZl9LZqd9Qc1B4wwyebb2gVJ+aP9wcYjcnWzhbvcqZsYf2GuAId4XVDpvRsyx6577Ub2C6A7vCD0kWzp9iklT9tCsowMbXeAJe4lcGlFJaqnK+Rj0ooXY8m1iWmlfZDfu5IDST4vskrs/wZ7pa1HF7UaWulOt655gpoS13YeJHNNz4j9h4SPZnbfCTvjpk/5NIKzxAa5dehDQhxhgtap82MkZAqWL5/RmQq5Xzecp0ANUgRwYCt9QgfjjWxZ7VV2uQMsFqoFxf24xRc2eh7HMAl/hA3oKqJqlyZ0EcIMD95OPBB0133rmjJwL/3e72AMhpwIDAQAB"


        val publicKeyBytes = Base64.getDecoder().decode(key)
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(publicKeyBytes))

        val claims = Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(token).body

        val sessionId = claims.subject
        val issuer = claims.issuer
        val issuedAt = claims.issuedAt
//        val expiresAt = claims.expiration
        val audience = claims.audience
        val subject = claims.subject
        val tokenType = claims.get("typ", String::class.java)
        val jwtId = claims.id

        return KeycloakLogoutToken(
            sessionId,
            issuer,
            issuedAt,
//            expiresAt,
            audience,
            subject,
            tokenType,
            jwtId
        )
    }

//    @PostMapping("/sso-logout", produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun backchannelLogout(@RequestParam("logout_token") logoutToken: String) {
//
//        val tokenParts = logoutToken.split(".")
//
//
//        val decoder = Base64.getUrlDecoder()
//
//        val header = String(decoder.decode(tokenParts[0]))
//        val payload = String(decoder.decode(tokenParts[1]))
//        val signature = String(decoder.decode(tokenParts[2]))
//
//
//        val keycloakTokenPayload = Json.decodeFromString<KeycloakTokenPayload>(payload)
//        println("sid: ${keycloakTokenPayload.sid}")
//
//
//
//    }
}