package com.teamsparta.todoproject.domain.post.repository

import com.teamsparta.todoproject.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
}