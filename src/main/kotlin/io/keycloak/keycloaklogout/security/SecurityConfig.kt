package io.keycloak.keycloaklogout.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.web.session.HttpSessionEventPublisher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.util.stream.Collectors


@Configuration
@EnableWebSecurity
internal class SecurityConfig(private val keycloakLogoutHandler: KeycloakLogoutHandler,
    private val springSecurityProperties: SpringSecurityProperties) {



    @Bean
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

    @Bean
    protected fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        return RegisterSessionAuthenticationStrategy(sessionRegistry())
    }

    @Bean
    fun httpSessionEventPublisher(): HttpSessionEventPublisher {
        return HttpSessionEventPublisher()
    }

    @Bean
    @Throws(Exception::class)
    fun resourceServerFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { auth ->
            auth
                .requestMatchers(AntPathRequestMatcher("/mypage*"))
                .authenticated()
                .requestMatchers(
                    AntPathRequestMatcher("/"),
                    AntPathRequestMatcher("/sso-logout")
                )
                .permitAll()
                .anyRequest()
                .authenticated()
        }
        http.oauth2ResourceServer { oauth2: OAuth2ResourceServerConfigurer<HttpSecurity?> ->
            oauth2
                .jwt {
                    // Customizer.default로 하면 jwtDecoder를 직접 만들어야함. 아래처럼 하면 알아서만듬
                    // OAuth2ResourceServerConfigurer 참고
                    jwt -> jwt.jwkSetUri(springSecurityProperties.issuerUri)
                }
        }
        http.oauth2Login { auth ->
            auth.defaultSuccessUrl("/mypage")
        }.logout { logout: LogoutConfigurer<HttpSecurity?> ->
            logout.addLogoutHandler(
                keycloakLogoutHandler
            ).logoutSuccessUrl("/")
                .logoutUrl("/logout")
        }

        http.csrf { csrf -> csrf.disable() }
        return http.build()
    }

    @Bean
    fun userAuthoritiesMapperForKeycloak(): GrantedAuthoritiesMapper {
        return GrantedAuthoritiesMapper { authorities: Collection<GrantedAuthority> ->
            val mappedAuthorities: Set<GrantedAuthority> = hashSetOf()
            val authority = authorities.iterator().next()
            val isOidc = authority is OidcUserAuthority
            if (isOidc) {
                val oidcUserAuthority = authority as OidcUserAuthority
                val userInfo = oidcUserAuthority.userInfo

                // Tokens can be configured to return roles under
                // Groups or REALM ACCESS hence have to check both
                if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
                    val realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM)
                    val roles = realmAccess[ROLES_CLAIM] as Collection<String>
                    mappedAuthorities.plus(generateAuthoritiesFromClaim(roles))
                } else if (userInfo.hasClaim(GROUPS)) {
                    val roles = userInfo.getClaim<Any>(
                        GROUPS
                    ) as Collection<String>
                    mappedAuthorities.plus(generateAuthoritiesFromClaim(roles))
                }
            } else {
                val oauth2UserAuthority = authority as OAuth2UserAuthority
                val userAttributes = oauth2UserAuthority.attributes
                if (userAttributes.containsKey(REALM_ACCESS_CLAIM)) {
                    val realmAccess = userAttributes[REALM_ACCESS_CLAIM] as Map<String, Any>?
                    val roles = realmAccess!![ROLES_CLAIM] as Collection<String>?
                    mappedAuthorities.plus(generateAuthoritiesFromClaim(roles))
                }
            }
            mappedAuthorities
        }
    }

    fun generateAuthoritiesFromClaim(roles: Collection<String>?): Collection<GrantedAuthority> {
        return roles!!.stream().map { role: String -> SimpleGrantedAuthority("ROLE_$role") }
            .collect(
                Collectors.toList()
            )
    }

    companion object {
        private const val GROUPS = "groups"
        private const val REALM_ACCESS_CLAIM = "realm_access"
        private const val ROLES_CLAIM = "roles"
    }
}