package com.teamsparta.todoproject.domain.user.service

import com.teamsparta.todoproject.domain.user.dto.*
import com.teamsparta.todoproject.infra.security.UserPrincipal

interface UserService {

    fun getUserProfileById(userId: Long): UserResponse

    fun searchUserName(name: String): List<UserResponse>?

    fun signUp(request: SignUpRequest): UserResponse

    fun login(request: LoginRequest): LoginResponse

    fun updateUserProfile(userPrincipal: UserPrincipal, updateUserProfileRequest: UpdateUserProfileRequest): UserResponse//userprincipal 추가

    fun deleteUserProfile(userPrincipal: UserPrincipal)
}