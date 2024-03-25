package io.keycloak.keycloaklogout.dto

import java.util.*

data class KeycloakLogoutToken(
    val sessionId: String,
    val issuer: String,
    val issuedAt: Date,
//    val expiresAt: Date,
    val audience: String,
    val subject: String,
    val tokenType: String,
    val jwtId: String
)