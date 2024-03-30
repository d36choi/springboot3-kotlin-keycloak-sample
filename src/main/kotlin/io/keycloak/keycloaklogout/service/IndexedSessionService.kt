package io.keycloak.keycloaklogout.service;

import org.springframework.session.Session;

import java.security.Principal;
import java.util.Collection;

public interface IndexedSessionService {

    void removeAllByPrincipalUsername(String username);

    Collection<? extends Session> getAllSessions(Principal principal);
}
