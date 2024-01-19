package com.teamsparta.todoproject.domain.post.dto

data class UpdatePostStatusRequest(
    val status: Boolean//true, false 두가지만 하기위해 boolean
)
