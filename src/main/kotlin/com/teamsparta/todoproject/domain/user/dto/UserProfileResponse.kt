package com.teamsparta.todoproject.domain.user.dto

import com.teamsparta.todoproject.domain.user.model.User

data class UserProfileResponse(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val introduce: String
)
{
    companion object {
        fun from(user: User) = UserProfileResponse(
            id = user.id!!,
            name = user.profile.name,
            email = user.email,
            role = user.role.name,
            introduce = user.profile.introduce
        )
    }
}
