package com.teamsparta.todoproject.domain.user.repository

import com.teamsparta.todoproject.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>{

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User?

    fun searchUserName(name: String): List<User>//사용자 이름으로 검색
}