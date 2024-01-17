package com.teamsparta.todoproject.domain.exception

data class PostNotFoundException(val postName: String, val id: Long?):
        RuntimeException("Model $postName not found with given id: $id")
