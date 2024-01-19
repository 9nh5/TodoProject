package com.teamsparta.todoproject.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val description: String,
    val createdAt: LocalDateTime,
    val post_id: Long,
    val user_id: Long
)
