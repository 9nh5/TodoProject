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
import io.swagger.v3.oas.annotations.Operation
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

    @Operation(summary = "할 일 카드 목록 조회")
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

    @Operation(summary = "할 일 카드 제목 검색")
    @GetMapping("/searchtitle")
    fun searchPostListByTitle(@RequestParam(value = "title")title : String): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.searchPostListByTitle(title))
    }

    @Operation(summary = "할 일 카드 이름 검색")
    @GetMapping("/searchname")
    fun searchPostListByName(@RequestParam(value = "name")name : String): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.searchPostListByName(name))
    }

    @Operation(summary = "할 일 카드 단건 조회")
    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId: Long): ResponseEntity<PostResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostById(postId))
    }

    @Operation(summary = "할 일 카드 작성")
    @PostMapping
    fun createPost(
        @AuthenticationPrincipal userPrincipal:UserPrincipal,
        @RequestBody createPostRequest: CreatePostRequest): ResponseEntity<PostResponse> {
        println("userPrincipal:${userPrincipal}")
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(userPrincipal, createPostRequest))
    }

    @Operation(summary = "할 일 카드 수정")
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


    @Operation(summary = "할 일 완료")
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

    @Operation(summary = "할 일 카드 삭제")
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