package com.teamsparta.todoproject.domain.user.dto

data class LoginResponse(
    val accessToken: String,
    val email: String,
    val role: String
)
