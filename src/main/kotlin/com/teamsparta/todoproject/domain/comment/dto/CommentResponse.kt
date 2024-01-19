package com.teamsparta.todoproject.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val description: String,
    val createdAt: LocalDateTime,
    val post_id: Long,
    val user_id: Long,//유저 아이디 불러오기 위해 추가, 댓글 조회 했을 때 사용자 아이디가 나온다
    val name: String//유저 이름 불러오기 위해 추가, 댓글 조회 했을 때 사용자 이름이 나온다
)
