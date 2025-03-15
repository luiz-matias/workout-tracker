package com.luizmatias.workout_tracker.config.api.security

import com.luizmatias.workout_tracker.config.api.exception.UnauthorizedEntryPoint
import com.luizmatias.workout_tracker.model.user.AccountRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(
    private val accessTokenSecurityFilter: AccessTokenSecurityFilter,
) {
    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain =
        httpSecurity
            .authorizeHttpRequests {
                it
                    .requestMatchers("admin/**")
                    .hasAuthority(AccountRole.ADMIN.name)
                    .requestMatchers(HttpMethod.GET, "/actuator/**")
                    .hasAuthority(AccountRole.SYS_ADMIN.name)
                    .requestMatchers("/error")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/token/verify-email/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/token/password-reset/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }.exceptionHandling { it.authenticationEntryPoint(UnauthorizedEntryPoint()) }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .anonymous { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(accessTokenSecurityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
