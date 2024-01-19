package com.teamsparta.todoproject.domain.post.repository

import com.teamsparta.todoproject.domain.post.model.Post
import com.teamsparta.todoproject.domain.post.model.QPost
import com.teamsparta.todoproject.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl: QueryDslSupport(), CustomPostRepository {

    private  val post = QPost.post

    override fun searchPostListByTitle(title: String) : List<Post> {
        return queryFactory.selectFrom(post)
            .where(post.title.containsIgnoreCase(title))
            .fetch()
    }
}