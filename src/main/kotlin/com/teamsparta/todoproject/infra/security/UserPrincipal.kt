package com.teamsparta.todoproject.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(//로그인할 때 헤더에 담을 정보
    val id: Long,
    val email: String,
    val authorities: Collection<GrantedAuthority>
) {
    constructor(id: Long, email: String, roles: Set<String>): this(
        id,
        email,
        roles.map { SimpleGrantedAuthority("ROLE_$it") }
    )
}
