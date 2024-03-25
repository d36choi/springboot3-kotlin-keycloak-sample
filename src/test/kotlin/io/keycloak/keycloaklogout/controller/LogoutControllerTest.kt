package io.keycloak.keycloaklogout.controller

import io.jsonwebtoken.Jwts
import org.junit.jupiter.api.Test
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.*

class LogoutControllerTest() {


    @Test
    fun test() {
        val logoutToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJTSk50LWloUTU2ekhJVENrZThoaVJUMXd5S0hPTDFfSmo5dUJHRlUxVW0wIn0.eyJpYXQiOjE3MTEzNzkyMjksImp0aSI6Ijg1YjMxZGMwLTk5M2UtNDJkOS05MmZiLWNmYTUwYTVkOTI3MCIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6MTgwODAvcmVhbG1zL215cmVhbG0iLCJhdWQiOiJteWJsb2ciLCJzdWIiOiI5ZWEwNDc5Mi1jZTJhLTRlNjctYTNiZC1iYmRiMjdlYzgzN2UiLCJ0eXAiOiJMb2dvdXQiLCJzaWQiOiJlZGRkNjAwOS03OTQ5LTRmMDgtYTY4NS04NzFiMGZlY2I2NjEiLCJldmVudHMiOnsiaHR0cDovL3NjaGVtYXMub3BlbmlkLm5ldC9ldmVudC9iYWNrY2hhbm5lbC1sb2dvdXQiOnt9fX0.qyryWVp7hHfIJo0XQt0DEihk7ZY8BrDr5LV1G-Uwa-IOJ2rCii4s_f6a1z1sKYee6BLtmMFT_f1WwzSEEoIhUIpDn2k_DHkEcHu_9fCmNX0iD1kd7Ed9G-cMAmuJqvaNjnQxU9n3sy9gP4oM2dV4ibGT3aoPVGAaMNx04dmUQPtbCih6c_VvKd8tZ61ah6c8XRX8phK2-Wjd6HSta34KQs23bLXB2-l_O_jZdammhbnxqkDglzFKfg10_5DC1hV_ZNqUeiqeIhczOuldwcXzFkeEWj6OhHAiy-1iC5V9DD3g2pGFkDv_gwkc0YQ0WUOTEXgK3Ziud-IOnjk7rg5EkQ"
        val key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsUW5qAsGITtZuWghpDanZl9LZqd9Qc1B4wwyebb2gVJ+aP9wcYjcnWzhbvcqZsYf2GuAId4XVDpvRsyx6577Ub2C6A7vCD0kWzp9iklT9tCsowMbXeAJe4lcGlFJaqnK+Rj0ooXY8m1iWmlfZDfu5IDST4vskrs/wZ7pa1HF7UaWulOt655gpoS13YeJHNNz4j9h4SPZnbfCTvjpk/5NIKzxAa5dehDQhxhgtap82MkZAqWL5/RmQq5Xzecp0ANUgRwYCt9QgfjjWxZ7VV2uQMsFqoFxf24xRc2eh7HMAl/hA3oKqJqlyZ0EcIMD95OPBB0133rmjJwL/3e72AMhpwIDAQAB"


        val publicKeyBytes = Base64.getDecoder().decode(key)
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(publicKeyBytes))

        val claims = Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(logoutToken).body


        print(claims)
    }
}