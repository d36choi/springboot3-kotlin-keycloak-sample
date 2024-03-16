package io.keycloak.keycloaklogout.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal


@Controller
class MyPageController {

    @GetMapping("/mypage")
    fun myPage(model: Model, principal: Principal): String {
        model["title"] = "myPage"
        model["id"] = principal.name
        return "mypage"
    }
}