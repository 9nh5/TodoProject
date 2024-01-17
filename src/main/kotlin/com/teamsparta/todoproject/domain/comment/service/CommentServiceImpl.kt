package com.teamsparta.todoproject.domain.comment.service

import com.teamsparta.todoproject.domain.comment.dto.CommentResponse
import com.teamsparta.todoproject.domain.comment.dto.CreateCommentRequest
import com.teamsparta.todoproject.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todoproject.domain.comment.exception.CommentNotFoundException
import com.teamsparta.todoproject.domain.comment.model.Comment
import com.teamsparta.todoproject.domain.comment.model.toResponse
import com.teamsparta.todoproject.domain.comment.repository.CommentRepository
import com.teamsparta.todoproject.domain.exception.PostNotFoundException
import com.teamsparta.todoproject.domain.post.repository.PostRepository
import com.teamsparta.todoproject.domain.user.exception.UserNotFoundException
import com.teamsparta.todoproject.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : CommentService{

    override fun getAllCommentList(postId: Long): List<CommentResponse> {
        val post = postRepository.findByIdOrNull(postId) ?: throw CommentNotFoundException("Post", postId)
        return post.comments.map { it.toResponse() }
    }

    @Transactional
    override fun createComment(postId: Long, request: CreateCommentRequest): CommentResponse {
        val post = postRepository.findByIdOrNull(postId)?: throw CommentNotFoundException("Post", postId)
        val user = userRepository.findByIdOrNull(request.userId)?: throw UserNotFoundException("User", null)
        val comment = Comment(
            description = request.description,
            post = post,
            user = user
        )
        post.createComment(comment)
        postRepository.save(post)
        return comment.toResponse()
    }

    @Transactional
    override fun updateComment(postId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse {
        val comment = commentRepository.findByPostIdAndId(postId, commentId)?: throw CommentNotFoundException("Comment", commentId)
        val (description) = request

        comment.description = description

        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    override fun deleteComment(postId: Long, commentId: Long) {
        val post = postRepository.findByIdOrNull(postId)?: throw PostNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId)?: throw CommentNotFoundException("Comment", commentId)
        post.deleteComment(comment)
        postRepository.save(post)
    }
}