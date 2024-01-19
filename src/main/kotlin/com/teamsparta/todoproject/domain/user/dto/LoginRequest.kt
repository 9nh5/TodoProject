package com.teamsparta.todoproject.domain.user.dto

data class LoginRequest(//로그인 할 때 필요한 거만 요청
    val email: String,
    val password: String
)
