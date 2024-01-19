package com.teamsparta.todoproject.domain.user.repository

import com.teamsparta.todoproject.domain.user.model.User

interface CustomUserRepository {
    fun searchUserName(name: String): List<User>
}