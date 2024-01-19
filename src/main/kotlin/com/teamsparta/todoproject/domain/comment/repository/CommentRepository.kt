package com.teamsparta.todoproject.domain.comment.repository

import com.teamsparta.todoproject.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByPostIdAndId(postId: Long, commentId: Long): Comment?//아이디 두개 불러와야되니까 postId 와(And) Id(commentId)를 찾는다라는 뜻
}