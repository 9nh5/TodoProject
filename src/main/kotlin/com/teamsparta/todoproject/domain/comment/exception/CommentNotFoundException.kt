package com.teamsparta.todoproject.domain.comment.exception

data class CommentNotFoundException(val commentName: String, val commentId: Long)
    :RuntimeException("$commentName not found with give id$commentId")