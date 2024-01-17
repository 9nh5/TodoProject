package com.teamsparta.todoproject.domain.comment.dto

data class CreateCommentRequest(
    val userId: Long,
    val description: String
)
