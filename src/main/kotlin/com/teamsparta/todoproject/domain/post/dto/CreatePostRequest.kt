package com.teamsparta.todoproject.domain.post.dto

data class CreatePostRequest (
    val userId: Long,
    val title: String,
    val content: String
)