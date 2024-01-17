package com.teamsparta.todoproject.domain.user.exception

data class UserNotFoundException(val User: String, val userId: Long?): RuntimeException(
    "User $User not found give id: $userId"
)
