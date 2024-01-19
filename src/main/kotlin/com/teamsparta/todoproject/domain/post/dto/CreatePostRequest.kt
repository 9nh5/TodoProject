package com.teamsparta.todoproject.domain.post.dto

data class CreatePostRequest (//로그인한 사용자에 이미 유저아이디 들어가 있으니까 유저아이디 필요x
    val title: String,
    val content: String
)