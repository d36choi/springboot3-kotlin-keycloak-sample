package io.keycloak.keycloaklogout.service

import org.slf4j.LoggerFactory
import org.springframework.session.FindByIndexNameSessionRepository
import org.springframework.session.Session
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class IndexedSessionServiceImpl(val sessionRepository: FindByIndexNameSessionRepository<out Session>) :
    IndexedSessionService {
    override fun removeAllByPrincipalUsername(username: String) {
        val byPrincipalName = sessionRepository.findByPrincipalName(username)
        for (value in byPrincipalName.values) {
            logger.info("remove session:: {}", value.id)
            sessionRepository.deleteById(value.id)
        }
    }

    override fun getAllSessions(principal: Principal): Collection<Session> {
        return sessionRepository.findByPrincipalName(principal.name).values
    }

    companion object {
        private val logger = LoggerFactory.getLogger(IndexedSessionService::class.java)
    }
}