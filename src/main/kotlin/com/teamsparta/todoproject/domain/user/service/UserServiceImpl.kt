package com.teamsparta.todoproject.domain.user.service

import com.teamsparta.todoproject.domain.exception.dto.NotAuthorizationException
import com.teamsparta.todoproject.domain.user.dto.*
import com.teamsparta.todoproject.domain.user.exception.InvalidCredentialException
import com.teamsparta.todoproject.domain.user.exception.UserNotFoundException
import com.teamsparta.todoproject.domain.user.model.Profile
import com.teamsparta.todoproject.domain.user.model.User
import com.teamsparta.todoproject.domain.user.model.UserRole
import com.teamsparta.todoproject.domain.user.model.toResponse
import com.teamsparta.todoproject.domain.user.repository.UserRepository
import com.teamsparta.todoproject.infra.security.UserPrincipal
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

    override fun getUserProfileById(userId: Long): UserResponse {//사용자 아이디로 조회
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException("User", userId)
        return user.toResponse()
    }

    override fun searchUserName(name: String): List<UserResponse>? {//사용자 이름으로 검색
        return userRepository.searchUserName(name).map { it.toResponse() }
    }

    override fun login(request: LoginRequest): LoginResponse {//로그인
        val user = userRepository.findByEmail(request.email) ?: throw UserNotFoundException("User", null)

        if(!passwordEncoder.matches(request.password, user.password)) throw InvalidCredentialException()//예외처리

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
                role = user.role.name
            ), user.email, user.role.name
        )
    }

    override fun signUp(request: SignUpRequest): UserResponse {//회원가입
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("이미 사용중인 이메일입니다")
        }
        return userRepository.save(
            User(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                profile = Profile(
                    name = request.name,
                    introduce = request.introduce
                ),
                role = when (request.role) {
                    "User" -> UserRole.User
                    "Admin" -> UserRole.Admin
                    else -> throw IllegalArgumentException("Invalid role")
                }
            )
        ).toResponse()
    }

    override fun updateUserProfile(userPrincipal: UserPrincipal, updateUserProfileRequest: UpdateUserProfileRequest): UserResponse {//로그인한 본인만 자기 프로필 수정 가능, userprincipal 추가
        val user = userRepository.findByIdOrNull(userPrincipal.id)?: throw UserNotFoundException("User", null)//로그인한 본인 아이디 확인
        if(user.id != userPrincipal.id) throw NotAuthorizationException()//본인 아이디가 일치하는지 확인 아니면 예외처리
        user.profile = Profile(
            name = updateUserProfileRequest.name,
            introduce = updateUserProfileRequest.introduce
        )
        return userRepository.save(user).toResponse()
    }

    override fun deleteUserProfile(userPrincipal: UserPrincipal) {//프로필 삭제, 본인만 삭제 가능
        val user = userRepository.findByIdOrNull(userPrincipal.id)?: throw UserNotFoundException("User", null)//로그인한 본인 아이디 확인
        if(user.id != userPrincipal.id) throw NotAuthorizationException()//본인 아이디가 일치하는지 확인 아니면 예외처리
        userRepository.delete(user)
    }


}