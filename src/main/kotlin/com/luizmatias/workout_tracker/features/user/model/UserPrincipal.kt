package com.luizmatias.workout_tracker.features.user.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(
    val user: User,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>(SimpleGrantedAuthority(AccountRole.USER.name))
        if (user.role == AccountRole.SYS_ADMIN) {
            authorities.add(SimpleGrantedAuthority(AccountRole.SYS_ADMIN.name))
            authorities.add(SimpleGrantedAuthority(AccountRole.ADMIN.name))
        }
        if (user.role == AccountRole.ADMIN) {
            authorities.add(SimpleGrantedAuthority(AccountRole.ADMIN.name))
        }
        return authorities
    }

    override fun getUsername() = user.email

    override fun getPassword() = user.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = user.isEnabled
}
