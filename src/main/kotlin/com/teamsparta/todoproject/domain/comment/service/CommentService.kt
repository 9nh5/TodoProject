package com.teamsparta.todoproject.domain.comment.service

import com.teamsparta.todoproject.domain.comment.dto.CommentResponse
import com.teamsparta.todoproject.domain.comment.dto.CreateCommentRequest
import com.teamsparta.todoproject.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todoproject.infra.security.UserPrincipal

interface CommentService {

    fun getAllCommentList(postId: Long): List<CommentResponse>

//    fun getCommentById(postId: Long, commentId: Long): CommentResponse

    fun createComment(userPrincipal: UserPrincipal, postId: Long, request: CreateCommentRequest): CommentResponse//userPrincipal 추가

    fun updateComment(userPrincipal: UserPrincipal, postId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse//userPrincipal 추가

    fun deleteComment(userPrincipal: UserPrincipal, postId: Long, commentId: Long)//userPrincipal 추가
}