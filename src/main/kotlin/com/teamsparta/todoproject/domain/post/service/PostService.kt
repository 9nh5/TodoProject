package com.teamsparta.todoproject.domain.post.service

import com.teamsparta.todoproject.domain.post.dto.CreatePostRequest
import com.teamsparta.todoproject.domain.post.dto.PostResponse
import com.teamsparta.todoproject.domain.post.dto.UpdatePostRequest
import com.teamsparta.todoproject.domain.post.dto.UpdatePostStatusRequest
import com.teamsparta.todoproject.infra.security.UserPrincipal

interface PostService {

    fun getAllPostList(): List<PostResponse>

    fun getPostById(postId: Long): PostResponse

    fun createPost(userPrincipal: UserPrincipal, request: CreatePostRequest): PostResponse

    fun updatePost(userPrincipal: UserPrincipal, postId: Long, request: UpdatePostRequest): PostResponse

    fun updatePostStatus(userPrincipal: UserPrincipal, postId: Long, request: UpdatePostStatusRequest): PostResponse

    fun deletePost(userPrincipal: UserPrincipal, PostId: Long)

    fun searchPostList(title: String): List<PostResponse>?
}