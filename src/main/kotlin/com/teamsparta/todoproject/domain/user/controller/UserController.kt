package com.teamsparta.todoproject.domain.user.controller

import com.teamsparta.todoproject.domain.user.dto.LoginRequest
import com.teamsparta.todoproject.domain.user.dto.LoginResponse
import com.teamsparta.todoproject.domain.user.dto.SignUpRequest
import com.teamsparta.todoproject.domain.user.dto.UserResponse
import com.teamsparta.todoproject.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService
) {

    @GetMapping("/users/{userId}")
    fun getUserProfile(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getUserProfileById(userId))
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signUp(signUpRequest))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.login(loginRequest))
    }
}