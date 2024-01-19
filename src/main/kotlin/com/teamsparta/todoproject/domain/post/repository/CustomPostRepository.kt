package com.teamsparta.todoproject.domain.post.repository

import com.teamsparta.todoproject.domain.post.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomPostRepository {

    fun findByPageable(pageable: Pageable): Page<Post>
    fun searchPostListByTitle(title: String): List<Post>
    fun searchPostListByName(name: String): List<Post>
}