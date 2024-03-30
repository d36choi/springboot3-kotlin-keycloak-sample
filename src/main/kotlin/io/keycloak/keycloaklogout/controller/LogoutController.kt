package io.keycloak.keycloaklogout.controller

import io.keycloak.keycloaklogout.service.IndexedSessionService
import io.keycloak.keycloaklogout.service.LogoutService
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class LogoutController(
    private val logoutService: LogoutService
) {

    private val logger = LoggerFactory.getLogger(IndexedSessionService::class.java)

    @PostMapping("/sso-logout", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun backChannelLogout(@RequestParam("logout_token") logoutToken: String) {
        logger.info("/sso-logout: 백채널 로그아웃 API 호출됨")
        logoutService.backChannelLogout(logoutToken)
    }

}