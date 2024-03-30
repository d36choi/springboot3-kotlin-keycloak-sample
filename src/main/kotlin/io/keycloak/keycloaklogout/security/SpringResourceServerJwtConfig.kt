package io.keycloak.keycloaklogout.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
class SpringResourceServerJwtConfig {
    lateinit var issuerUri: String
}