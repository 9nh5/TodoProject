package com.teamsparta.todoproject.domain.user.dto

data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val role: String,
    val introduce: String
)
