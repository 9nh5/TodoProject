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

    @Operation(summary = "게시글/댓글 조회")
    @GetMapping
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
    @PostMapping
    fun createComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody createCommentRequest: CreateCommentRequest
    ): ResponseEntity<CommentResponse> {
        println("userPrincipal:${userPrincipal}")
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(userPrincipal, postId, createCommentRequest))
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(userPrincipal, postId, commentId, updateCommentRequest))
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping ("/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        commentService.deleteComment(userPrincipal, postId, commentId)
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