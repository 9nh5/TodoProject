package com.teamsparta.todoproject.domain.comment.repository

import com.teamsparta.todoproject.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByPostIdAndId(postId: Long, commentId: Long): Comment?
}