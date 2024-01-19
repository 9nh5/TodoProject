package com.teamsparta.todoproject.domain.user.repository

import com.teamsparta.todoproject.domain.user.model.User

interface CustomUserRepository {
    fun searchUserName(name: String): List<User>//사용자 이름으로 검색
}