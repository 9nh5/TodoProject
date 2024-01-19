package com.teamsparta.todoproject.domain.post.repository

import com.teamsparta.todoproject.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long>, CustomPostRepository {

    fun findByUserIdAndId(userId: Long, id: Long): Post?//이건 뭐지 안쓰는듯

}