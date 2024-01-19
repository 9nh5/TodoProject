package com.teamsparta.todoproject.domain.user.repository

import com.teamsparta.todoproject.domain.user.model.QUser
import com.teamsparta.todoproject.domain.user.model.User
import com.teamsparta.todoproject.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl: QueryDslSupport(), CustomUserRepository {

    private val user = QUser.user

    override fun searchUserName(name: String): List<User> {
        return queryFactory.selectFrom(user)
            .where(user.profile.name.containsIgnoreCase(name))
            .fetch()
    }
}
//사용자 이름으로 검색하는 쿼리