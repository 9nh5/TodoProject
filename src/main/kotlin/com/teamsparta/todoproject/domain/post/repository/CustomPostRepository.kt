package com.teamsparta.todoproject.domain.post.repository

import com.teamsparta.todoproject.domain.post.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomPostRepository {

    fun findByPageable(pageable: Pageable): Page<Post>//목록 설정하기 위함
    fun searchPostListByTitle(title: String): List<Post>//제목으로 검색하려고
    fun searchPostListByName(name: String): List<Post>//사용자 이름으로 검색하려고
}