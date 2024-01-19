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

    @Operation(summary = "할 일 카드 목록 조회")//그냥 swagger에서 보여주는 용도 없어도댐
    @GetMapping//할 일 카드 목록 조회
    fun getPostList(
        @PageableDefault(//목록 설정해서 조회
            size = 15,
            sort = ["createdAt"]
        ) pageable: Pageable): ResponseEntity<Page<PostResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPaginatedPostList(pageable))
    }

    @Operation(summary = "할 일 카드 제목 검색")
    @GetMapping("/searchtitle")//제목으로 할 일 카드 검색
    fun searchPostListByTitle(@RequestParam(value = "title")title : String): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.searchPostListByTitle(title))
    }

    @Operation(summary = "할 일 카드 사용자 이름 검색")
    @GetMapping("/searchname")//사용자 이름으로 할 일 카드 검색
    fun searchPostListByName(@RequestParam(value = "name")name : String): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.searchPostListByName(name))
    }

    @Operation(summary = "할 일 카드 단건 조회")
    @GetMapping("/{postId}")//포스트 아이디로 단건 조회
    fun getPost(@PathVariable postId: Long): ResponseEntity<PostResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostById(postId))
    }

    @Operation(summary = "할 일 카드 작성")
    @PostMapping//할 일 카드 작성
    fun createPost(
        @AuthenticationPrincipal userPrincipal:UserPrincipal,//로그인 객체 가져오는 어노테이션, 로그인한 사용자가 작성자와 일치하는지 확인하기위함
        @RequestBody createPostRequest: CreatePostRequest): ResponseEntity<PostResponse> {
        println("userPrincipal:${userPrincipal}")//이건 확인용으로 추가한거, 실행했을 때 로그에 뜸
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(userPrincipal, createPostRequest))
    }

    @Operation(summary = "할 일 카드 수정")
    @PutMapping("/{postId}")
    fun updatePost(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,//로그인 객체 가져오는 어노테이션, 로그인한 사용자가 작성자와 일치하는지 확인하기위함
        @PathVariable postId: Long,
        @RequestBody updatePostRequest: UpdatePostRequest): ResponseEntity<PostResponse> {
        println("userPrincipal:${userPrincipal}")//이건 확인용으로 추가한거, 실행했을 때 로그에 뜸
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(userPrincipal, postId, updatePostRequest))
    }


    @Operation(summary = "할 일 완료")
    @PatchMapping("/{postId}")
    fun updatePostStatus(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,//로그인 객체 가져오는 어노테이션, 로그인한 사용자가 작성자와 일치하는지 확인하기위함
        @PathVariable postId: Long,
        @RequestBody updatePostStatusRequest: UpdatePostStatusRequest): ResponseEntity<PostResponse> {
        println("userPrincipal:${userPrincipal}")//이건 확인용으로 추가한거, 실행했을 때 로그에 뜸
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePostStatus(userPrincipal, postId, updatePostStatusRequest))//principal추가
    }

    @Operation(summary = "할 일 카드 삭제")
    @DeleteMapping("/{postId}")
    fun deleteCard(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,//로그인 객체 가져오는 어노테이션, 로그인한 사용자가 작성자와 일치하는지 확인하기위함
        @PathVariable postId: Long): ResponseEntity<Unit> {
        println("userPrincipal:${userPrincipal}")//이건 확인용으로 추가한거, 실행했을 때 로그에 뜸
        postService.deletePost(userPrincipal, postId)//principal추가
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}