package io.keycloak.keycloaklogout.controller

import io.keycloak.keycloaklogout.service.IndexedSessionService
import jakarta.servlet.http.HttpSession
import org.springframework.session.Session
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal


@Controller
class MyPageController(
    val indexedSessionService: IndexedSessionService
) {

    @GetMapping("/mypage")
    fun myPage(model: Model, principal: Principal, session: HttpSession): String {
        model["title"] = "myPage"
        model["id"] = principal.name

        val allSessions: MutableCollection<out Session> = indexedSessionService.getAllSessions(principal)
        val toList = allSessions.toList()
        if (toList.isNotEmpty()) {
            println(toList[0].id)
        }
        return "mypage"
    }
}