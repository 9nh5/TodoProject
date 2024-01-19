package com.teamsparta.todoproject.domain.user.dto

data class UpdateUserProfileRequest (//프로필 수정할 것들만 요청
    val name: String,
    val introduce: String
)