package io.keycloak.keycloaklogout.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer
import org.springframework.session.web.http.CookieSerializer
import org.springframework.session.web.http.DefaultCookieSerializer

@Configuration
@EnableRedisIndexedHttpSession
class HttpSessionConfig : AbstractHttpSessionApplicationInitializer() {

    @Bean
    fun cookieSerializer(): CookieSerializer {
        val cookieSerializer = DefaultCookieSerializer()
        cookieSerializer.setCookieName("MYSESSIONID") // 세션 쿠키 이름 설정
        cookieSerializer.setCookiePath("/") // 쿠키 경로 설정
        cookieSerializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$") // 도메인 패턴 설정
        return cookieSerializer
    }
}