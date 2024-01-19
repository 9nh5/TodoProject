package com.teamsparta.todoproject.infra.security

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)//유저 , 관리자 권한
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAnyAuthority('ADMIN' or 'USER')")
annotation class UserAuthorize