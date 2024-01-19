package com.teamsparta.todoproject.domain.user.controller

import com.teamsparta.todoproject.domain.user.dto.*
import com.teamsparta.todoproject.domain.user.service.UserService
import com.teamsparta.todoproject.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "사용자 아이디 조회")
    @GetMapping("/users/{userId}")
    fun getUserProfile(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getUserProfileById(userId))
    }

    @Operation(summary = "사용자 이름 검색")
    @GetMapping("/searchuser")
    fun searchUserName(@RequestParam(value = "name")name : String): ResponseEntity<List<UserResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.searchUserName(name))
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signUp(signUpRequest))
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.login(loginRequest))
    }

    @Operation(summary = "프로필 수정")
    @PutMapping("/updateprofile")
    fun updateUserProfile(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody updateUserProfileRequest: UpdateUserProfileRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUserProfile(userPrincipal, updateUserProfileRequest))
    }
}