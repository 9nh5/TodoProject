package com.teamsparta.todoproject.domain.post.dto

import com.teamsparta.todoproject.domain.comment.dto.CommentResponse
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val status: Boolean,
    val user_id: Long,
    val name: String,
    val comments: List<CommentResponse>
)
