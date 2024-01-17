package com.teamsparta.todoproject.domain.post.service

import com.teamsparta.todoproject.domain.exception.PostNotFoundException
import com.teamsparta.todoproject.domain.post.dto.CreatePostRequest
import com.teamsparta.todoproject.domain.post.dto.PostResponse
import com.teamsparta.todoproject.domain.post.dto.UpdatePostStatusRequest
import com.teamsparta.todoproject.domain.post.dto.UpdatePostRequest
import com.teamsparta.todoproject.domain.post.model.Post
//import com.teamsparta.todoproject.domain.post.model.PostStatus
import com.teamsparta.todoproject.domain.post.model.toResponse
import com.teamsparta.todoproject.domain.post.repository.PostRepository
import com.teamsparta.todoproject.domain.user.exception.UserNotFoundException
import com.teamsparta.todoproject.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
): PostService{

    override fun getAllPostList(): List<PostResponse> {
        return postRepository.findAll().map {it.toResponse()}
    }

//    @StopWatch
    @Transactional
    override fun getPostById(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException("Post", postId)
        return post.toResponse()
    }

    @Transactional
    override fun createPost(request: CreatePostRequest): PostResponse {
        val user = userRepository.findByIdOrNull(request.userId)?: throw UserNotFoundException("User", null)
        return postRepository.save<Post?>(
            Post(
                user = user,
                title = request.title,
                content = request.content
            )
        ).toResponse()
    }

    @Transactional
    override fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val post = postRepository.findByIdOrNull(postId)?: throw PostNotFoundException("Post", postId)
        val (title, content) = request


        post.title = title
        post.content = content

        return postRepository.save(post).toResponse()
    }

    @Transactional
    override fun updatePostStatus(postId: Long, request: UpdatePostStatusRequest): PostResponse {
        val post = postRepository.findByIdOrNull(postId)?: throw PostNotFoundException("Post", postId)

        post.status = true

        return postRepository.save(post).toResponse()
    }


    @Transactional
    override fun deletePost(postId: Long) {
        val post = postRepository.findByIdOrNull(postId)?: throw PostNotFoundException("Post", postId)
        postRepository.delete(post)
    }
}