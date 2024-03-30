package io.keycloak.keycloaklogout.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;


@Service
public class IndexedSessionServiceImpl implements IndexedSessionService {

    private static final Logger logger = LoggerFactory.getLogger(IndexedSessionService.class);
    public final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    public IndexedSessionServiceImpl(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }


    @Override
    public void removeAllByPrincipalUsername(String username) {
        Map<String, ? extends Session> byPrincipalName = sessionRepository.findByPrincipalName(username);
        for (Session value : byPrincipalName.values()) {
            logger.info("remove session:: {}", value.getId());
            sessionRepository.deleteById(value.getId());
        }
    }

    @Override
    public Collection<? extends Session> getAllSessions(Principal principal) {
        return this.sessionRepository.findByPrincipalName(principal.getName()).values();
    }
}
