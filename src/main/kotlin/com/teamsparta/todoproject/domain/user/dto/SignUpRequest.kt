package com.teamsparta.todoproject.domain.user.dto

data class SignUpRequest(//회원가입 할 때 필요한거 요청
    val email: String,
    val password: String,
    val name: String,
    val role: String,
    val introduce: String
)
