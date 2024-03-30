package io.keycloak.keycloaklogout.service

interface LogoutService {

    fun backChannelLogout(logoutToken: String)
}