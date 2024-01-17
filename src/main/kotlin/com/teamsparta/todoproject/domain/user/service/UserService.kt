package com.teamsparta.todoproject.domain.user.service

import com.teamsparta.todoproject.domain.user.dto.LoginRequest
import com.teamsparta.todoproject.domain.user.dto.LoginResponse
import com.teamsparta.todoproject.domain.user.dto.SignUpRequest
import com.teamsparta.todoproject.domain.user.dto.UserResponse

interface UserService {

    fun getUserProfileById(UserId: Long): UserResponse

    fun signUp(request: SignUpRequest): UserResponse

    fun login(request: LoginRequest): LoginResponse
}