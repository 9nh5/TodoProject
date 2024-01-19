package com.teamsparta.todoproject.domain.user.dto

data class LoginResponse(//로그인했을 때 나오는 정보
    val accessToken: String,
    val email: String,
    val role: String
)
