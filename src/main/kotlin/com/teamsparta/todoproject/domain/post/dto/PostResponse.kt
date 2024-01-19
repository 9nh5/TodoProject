package com.teamsparta.todoproject.domain.post.dto

import com.teamsparta.todoproject.domain.comment.dto.CommentResponse
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val status: Boolean,
    val user_id: Long,//사용자 아이디 정보만 받아옴
    val name: String,//사용자 이름만 받아옴
    val comments: List<CommentResponse>
)
