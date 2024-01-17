package com.teamsparta.todoproject.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val description: String,
    val createdAt: LocalDateTime
)
