package com.teamsparta.todoproject.domain.comment.service

import com.teamsparta.todoproject.domain.comment.dto.CommentResponse
import com.teamsparta.todoproject.domain.comment.dto.CreateCommentRequest
import com.teamsparta.todoproject.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todoproject.domain.comment.exception.CommentNotFoundException
import com.teamsparta.todoproject.domain.comment.model.Comment
import com.teamsparta.todoproject.domain.comment.model.toResponse
import com.teamsparta.todoproject.domain.comment.repository.CommentRepository
import com.teamsparta.todoproject.domain.exception.PostNotFoundException
import com.teamsparta.todoproject.domain.exception.dto.NotAuthorizationException
import com.teamsparta.todoproject.domain.post.repository.PostRepository
import com.teamsparta.todoproject.domain.user.exception.UserNotFoundException
import com.teamsparta.todoproject.domain.user.repository.UserRepository
import com.teamsparta.todoproject.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,//각 repository와 연결
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : CommentService{

    override fun getAllCommentList(postId: Long): List<CommentResponse> {
        val post = postRepository.findByIdOrNull(postId) ?: throw CommentNotFoundException("Post", postId)
        return post.comments.map { it.toResponse() }
    }//해당 포스트 아이디로 검색해서 그 아이디에 있는 댓글들 조회. 없으면 예외처리

    @Transactional
    override fun createComment(userPrincipal: UserPrincipal, postId: Long, request: CreateCommentRequest): CommentResponse {//userPrincipal 추가
        val post = postRepository.findByIdOrNull(postId)?: throw CommentNotFoundException("Post", postId)
        val user = userRepository.findByIdOrNull(userPrincipal.id)?: throw UserNotFoundException("User", null)//로그인한 사용자 아이디 확인, 로그인한 사용자가 작성자와 일치하는지 확인하기위함
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
    override fun updateComment(userPrincipal: UserPrincipal, postId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse {//userPrincipal 추가
        val comment = commentRepository.findByPostIdAndId(postId, commentId)?: throw CommentNotFoundException("Comment", commentId)
        userRepository.findByIdOrNull(userPrincipal.id)?: throw UserNotFoundException("User", null)//로그인한 사용자 아이디 확인,
        if(comment.user.id != userPrincipal.id) throw NotAuthorizationException()//로그인한 사용자와 작성자가 일치하는지 확인하고 아니면 예외처리
        val (description) = request

        comment.description = description

        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    override fun deleteComment(userPrincipal: UserPrincipal, postId: Long, commentId: Long) {//userPrincipal 추가
        val post = postRepository.findByIdOrNull(postId)?: throw PostNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId)?: throw CommentNotFoundException("Comment", commentId)
        userRepository.findByIdOrNull(userPrincipal.id)?: throw UserNotFoundException("User", null)//로그인한 사용자 아이디 확인,
        if(comment.user.id != userPrincipal.id) throw NotAuthorizationException()//로그인한 사용자와 작성자가 일치하는지 확인하고 아니면 예외처리
        post.deleteComment(comment)
        postRepository.save(post)
    }
}