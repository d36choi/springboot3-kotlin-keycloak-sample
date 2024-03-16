package io.keycloak.keycloaklogout

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KeycloakLogoutApplication

fun main(args: Array<String>) {
	runApplication<KeycloakLogoutApplication>(*args)
}
