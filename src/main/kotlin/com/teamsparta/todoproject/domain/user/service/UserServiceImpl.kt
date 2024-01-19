package com.teamsparta.todoproject.domain.user.service

import com.teamsparta.todoproject.domain.user.dto.LoginRequest
import com.teamsparta.todoproject.domain.user.dto.LoginResponse
import com.teamsparta.todoproject.domain.user.dto.SignUpRequest
import com.teamsparta.todoproject.domain.user.dto.UserResponse
import com.teamsparta.todoproject.domain.user.exception.InvalidCredentialException
import com.teamsparta.todoproject.domain.user.exception.UserNotFoundException
import com.teamsparta.todoproject.domain.user.model.Profile
import com.teamsparta.todoproject.domain.user.model.User
import com.teamsparta.todoproject.domain.user.model.UserRole
import com.teamsparta.todoproject.domain.user.model.toResponse
import com.teamsparta.todoproject.domain.user.repository.UserRepository
import com.teamsparta.todoproject.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
): UserService{

    override fun getUserProfileById(userId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException("User", userId)
        return user.toResponse()
    }

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw UserNotFoundException("User", null)

        if(!passwordEncoder.matches(request.password, user.password)) throw InvalidCredentialException()

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
                role = user.role.name
            ), user.email, user.role.name
        )
    }

    override fun signUp(request: SignUpRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("이미 사용중인 이메일입니다")
        }
        return userRepository.save(
            User(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                profile = Profile(
                    name = request.name
                ),
                role = when (request.role) {
                    "User" -> UserRole.User
                    "Admin" -> UserRole.Admin
                    else -> throw IllegalArgumentException("Invalid role")
                }
            )
        ).toResponse()
    }


}