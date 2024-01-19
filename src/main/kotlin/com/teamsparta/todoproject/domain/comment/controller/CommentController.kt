package com.teamsparta.todoproject.domain.comment.controller

import com.teamsparta.todoproject.domain.comment.dto.CommentResponse
import com.teamsparta.todoproject.domain.comment.dto.CreateCommentRequest
import com.teamsparta.todoproject.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todoproject.domain.comment.exception.CommentNotFoundException
import com.teamsparta.todoproject.domain.comment.exception.dto.ErrorResponse
import com.teamsparta.todoproject.domain.comment.service.CommentService
import com.teamsparta.todoproject.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/posts/{postId}/comments")
@RestController
class CommentController(
    private val commentService: CommentService
) {

    @Operation(summary = "게시글/댓글 조회")//swagger에 제목처럼 표시해줌
    @GetMapping//댓글 목록 조회
    fun getCommentList(@PathVariable postId: Long): ResponseEntity<List<CommentResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getAllCommentList(postId))
    }

//    @GetMapping("/{commentId}")
//    fun getComment(@PathVariable postId: Long): ResponseEntity<CommentResponse>{
//        TODO()
//    }

    @Operation(summary = "댓글 작성")
    @PostMapping//댓글 작성
    fun createComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,//로그인 객체 가져오는 어노테이션, 로그인한 사용자가 작성자와 일치하는지 확인하기위함
        @PathVariable postId: Long,
        @RequestBody createCommentRequest: CreateCommentRequest
    ): ResponseEntity<CommentResponse> {
        println("userPrincipal:${userPrincipal}")//이거는 그냥 로그인 객체 잘 가져왔나 확인하는 용도, 실행시켰을 때 로그에 로그인 객체 나옴
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(userPrincipal, postId, createCommentRequest))//userPrincipal 추가
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")//댓글 수정
    fun updateComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,//동일
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(userPrincipal, postId, commentId, updateCommentRequest))//동일
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping ("/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,//동일
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        commentService.deleteComment(userPrincipal, postId, commentId)//동일
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @ExceptionHandler(CommentNotFoundException::class)
    fun handleCommentNotFoundException(e: CommentNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(message = e.message))
    }
}