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
    @GetMapping("/users/{userId}")//사용자 아이디로 조회
    fun getUserProfile(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getUserProfileById(userId))
    }

    @Operation(summary = "사용자 이름 검색")
    @GetMapping("/searchuser")//사용자 이름으로 검색하는 쿼리 사용
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
        @AuthenticationPrincipal userPrincipal: UserPrincipal,//로그인 객체 가져오는 어노테이션, 로그인한 사용자가 작성자와 일치하는지 확인하기위함
        @RequestBody updateUserProfileRequest: UpdateUserProfileRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUserProfile(userPrincipal, updateUserProfileRequest))//principal추가
    }

    @Operation(summary = "프로필 삭제")
    @DeleteMapping("/delete")
    fun deleteUserProfile(
        @AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<Unit> {//로그인 객체 가져오는 어노테이션, 로그인한 사용자가 작성자와 일치하는지 확인하기위함
        userService.deleteUserProfile(userPrincipal)//principal추가
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}