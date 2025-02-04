package com.luizmatias.workout_tracker.config.security

import com.luizmatias.workout_tracker.service.auth.jwt.JWTService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter @Autowired constructor(
    private val userDetailsService: UserDetailsService,
    private val jwtService: JWTService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwtToken = request.recoverToken() ?: run {
            filterChain.doFilter(request, response)
            return
        }

        val email = jwtService.validateAndGetSubjectFromToken(jwtToken) ?: run {
            filterChain.doFilter(request, response)
            return
        }

        val userDetails = userDetailsService.loadUserByUsername(email) ?: run {
            filterChain.doFilter(request, response)
            return
        }

        val authenticationToken = UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
        )

        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authenticationToken

        filterChain.doFilter(request, response)
    }

    fun HttpServletRequest.recoverToken(): String? {
        val authHeader = this.getHeader("Authorization") ?: return null
        return authHeader.replace("Bearer ", "")
    }
}