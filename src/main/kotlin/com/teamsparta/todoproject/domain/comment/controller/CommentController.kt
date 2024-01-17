package com.teamsparta.todoproject.domain.comment.controller

import com.teamsparta.todoproject.domain.comment.dto.CommentResponse
import com.teamsparta.todoproject.domain.comment.dto.CreateCommentRequest
import com.teamsparta.todoproject.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todoproject.domain.comment.exception.CommentNotFoundException
import com.teamsparta.todoproject.domain.comment.exception.dto.ErrorResponse
import com.teamsparta.todoproject.domain.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/posts/{postId}/comments")
@RestController
class CommentController(
    private val commentService: CommentService
) {

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

    @PostMapping
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody createCommentRequest: CreateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(postId, createCommentRequest))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(postId, commentId, updateCommentRequest))
    }

    @DeleteMapping ("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        commentService.deleteComment(postId, commentId)
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