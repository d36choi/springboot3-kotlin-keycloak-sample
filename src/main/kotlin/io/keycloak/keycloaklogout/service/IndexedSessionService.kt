package io.keycloak.keycloaklogout.service

import org.springframework.session.Session
import java.security.Principal

interface IndexedSessionService {
    fun removeAllByPrincipalUsername(username: String)
    fun getAllSessions(principal: Principal): Collection<Session>
}