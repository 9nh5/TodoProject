package com.teamsparta.todoproject.domain.post.controller

import com.teamsparta.todoproject.domain.exception.PostNotFoundException
import com.teamsparta.todoproject.domain.exception.dto.ErrorResponse
import com.teamsparta.todoproject.domain.post.dto.CreatePostRequest
import com.teamsparta.todoproject.domain.post.dto.PostResponse
import com.teamsparta.todoproject.domain.post.dto.UpdatePostRequest
import com.teamsparta.todoproject.domain.post.dto.UpdatePostStatusRequest
import com.teamsparta.todoproject.domain.post.service.PostService
import com.teamsparta.todoproject.domain.user.service.UserService
import com.teamsparta.todoproject.infra.security.UserAuthorize
import com.teamsparta.todoproject.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@UserAuthorize
@RequestMapping("/posts")
@RestController
class PostController(
    private val postService: PostService,
    private val userService: UserService
) {
    @ExceptionHandler(PostNotFoundException::class)
    fun handleModelNotFoundException(e: PostNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(e.message))
    }

    @GetMapping
    fun getPostList(
        @PageableDefault(
            size = 15,
            sort = ["createdAt"]
        ) pageable: Pageable): ResponseEntity<Page<PostResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPaginatedPostList(pageable))
    }

    @GetMapping("/search")
    fun searchPostList(@RequestParam(value = "title")title : String): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.searchPostList(title))
    }

    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId: Long): ResponseEntity<PostResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostById(postId))
    }

    @PostMapping
    fun createPost(
        @AuthenticationPrincipal userPrincipal:UserPrincipal,
        @RequestBody createPostRequest: CreatePostRequest): ResponseEntity<PostResponse> {
        println("userPrincipal:${userPrincipal}")
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(userPrincipal, createPostRequest))
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody updatePostRequest: UpdatePostRequest): ResponseEntity<PostResponse> {
        println("userPrincipal:${userPrincipal}")
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(userPrincipal, postId, updatePostRequest))
    }



    @PatchMapping("/{postId}")
    fun updatePostStatus(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody updatePostStatusRequest: UpdatePostStatusRequest): ResponseEntity<PostResponse> {
        println("userPrincipal:${userPrincipal}")
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePostStatus(userPrincipal, postId, updatePostStatusRequest))
    }

    @DeleteMapping("/{postId}")
    fun deleteCard(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable postId: Long): ResponseEntity<Unit> {
        println("userPrincipal:${userPrincipal}")
        postService.deletePost(userPrincipal, postId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}