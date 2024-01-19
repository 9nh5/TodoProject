package com.teamsparta.todoproject.infra.security

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAnyAuthority('ADMIN' or 'USER')")
annotation class UserAuthorize