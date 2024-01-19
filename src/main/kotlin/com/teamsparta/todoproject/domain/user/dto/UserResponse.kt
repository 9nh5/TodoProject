package com.teamsparta.todoproject.domain.user.dto

data class UserResponse(//기본 사용자 정보
    val id: Long,
    val email: String,
    val name: String,
    val role: String,
    val introduce: String
)
