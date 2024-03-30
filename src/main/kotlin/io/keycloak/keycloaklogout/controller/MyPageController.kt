package io.keycloak.keycloaklogout.controller

import jakarta.servlet.http.HttpSession
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping


@Controller
class MyPageController {

    @GetMapping("/mypage")
    fun myPage(model: Model, session: HttpSession, oauth: OAuth2AuthenticationToken): String {
        // OAuth2AuthenticationToken는 Principal구현체이며 OAuth2LoginAuthenticationFilter에 의해 생성(추측)
        model["title"] = "myPage"
        model["id"] = oauth.principal.attributes["preferred_username"]
        model["ip"] = (oauth.details as? WebAuthenticationDetails)?.remoteAddress ?: ""

        return "mypage"
    }
}