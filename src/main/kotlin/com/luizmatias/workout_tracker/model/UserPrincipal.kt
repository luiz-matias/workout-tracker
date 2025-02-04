package com.luizmatias.workout_tracker.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(private val user: User) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>(SimpleGrantedAuthority(AccountRole.USER.name))
        if (user.role == AccountRole.ADMIN) {
            authorities.add(SimpleGrantedAuthority(AccountRole.ADMIN.name))
        }
        return authorities
    }

    override fun getUsername() = user.email

    override fun getPassword() = user.password

    override fun isAccountNonExpired() = !user.isAccountExpired

    override fun isAccountNonLocked() = !user.isLocked

    override fun isCredentialsNonExpired() = !user.isCredentialsExpired

    override fun isEnabled() = user.isEnabled
}