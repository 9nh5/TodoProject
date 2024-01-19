package com.teamsparta.todoproject.domain.exception.dto

import com.teamsparta.todoproject.domain.exception.PostNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionalHandler {

    @ExceptionHandler(PostNotFoundException::class)
    fun handleModelNouFoundException(e: PostNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(e.message))
    }

    @ExceptionHandler(NotAuthorizationException::class)
    fun handleNotAuthorizationException(e: NotAuthorizationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(e.message))
    }
}