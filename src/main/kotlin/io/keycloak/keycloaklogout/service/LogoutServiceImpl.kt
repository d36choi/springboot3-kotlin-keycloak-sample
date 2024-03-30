package io.keycloak.keycloaklogout.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.keycloak.keycloaklogout.dto.KeycloakLogoutToken
import io.keycloak.keycloaklogout.security.SpringResourceServerJwtConfig
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.stereotype.Service
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Service
class LogoutServiceImpl(val springResourceServerJwtConfig: SpringResourceServerJwtConfig,
                        val indexedSessionService: IndexedSessionService,
                        private val jwtDecoder: JwtDecoder

    ): LogoutService {

    override fun backChannelLogout(logoutToken: String) {
        // 방법1. OAuth2ResourceServerConfigurer 를 통해 생성된 jwtDecoder bean을 주입해 이용합니다.
        jwtDecoder.decode(logoutToken).let {
            indexedSessionService.removeAllByPrincipalUsername(it.subject)
        }

        // 방법2. Jwts 라이브러리를 이용할 경우, JwtDecoder를 사용하지 않아도 됩니다.

        //
        //        parseTokenWithJwts(logoutToken).let {
        //            indexedSessionService.removeAllByPrincipalUsername(it.subject)
        //        }
    }

    private fun parseTokenWithJwts(token: String): KeycloakLogoutToken {
        // 방법2. Jwts 라이브러리를 이용할 경우, JwtDecoder를 사용하지 않아도 됩니다.
        val publicKey = springResourceServerJwtConfig.issuerUri
            .let { Base64.getDecoder().decode(it) }
            .let { KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(it)) }

        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(token).body


        with(claims) {
            return KeycloakLogoutToken(
                subject,
                issuer,
                issuedAt,
                audience,
                subject,
                get("typ", String::class.java),
                id
            )
        }
    }
}