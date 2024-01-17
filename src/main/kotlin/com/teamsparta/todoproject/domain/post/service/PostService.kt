package com.teamsparta.todoproject.domain.post.service

import com.teamsparta.todoproject.domain.post.dto.CreatePostRequest
import com.teamsparta.todoproject.domain.post.dto.PostResponse
import com.teamsparta.todoproject.domain.post.dto.UpdatePostStatusRequest
import com.teamsparta.todoproject.domain.post.dto.UpdatePostRequest

interface PostService {

    fun getAllPostList(): List<PostResponse>

    fun getPostById(postId: Long): PostResponse

    fun createPost(request: CreatePostRequest): PostResponse

    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse

    fun updatePostStatus(postId: Long, request: UpdatePostStatusRequest): PostResponse

    fun deletePost(PostId: Long)
}