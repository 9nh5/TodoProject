package com.teamsparta.todoproject.domain.post.repository

import com.teamsparta.todoproject.domain.comment.model.QComment
import com.teamsparta.todoproject.domain.post.model.Post
import com.teamsparta.todoproject.domain.post.model.QPost
import com.teamsparta.todoproject.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl: QueryDslSupport(), CustomPostRepository {

    private  val post = QPost.post

    override fun searchPostListByTitle(title: String) : List<Post> {
        return queryFactory.selectFrom(post)
            .where(post.title.containsIgnoreCase(title))
            .fetch()
    }

    override fun findByPageable(pageable: Pageable): Page<Post> {
        val totalCount = queryFactory.select(post.count()).from(post).fetchOne()?:0L

        val comment = QComment.comment
        val query = queryFactory.selectFrom(post)
            .leftJoin(post.comments, comment)
            .fetchJoin()
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        if (pageable.sort.isSorted) {
            when (pageable.sort.first()?.property) {
                "createdAt" -> query.orderBy(post.createdAt.desc())
                "id" -> query.orderBy(post.id.asc())
                "title" -> query.orderBy(post.title.asc())
                else -> query.orderBy(post.createdAt.desc())
            }
        } else {
            query.orderBy(post.createdAt.asc())
        }
        val contents = query.fetch()

        return PageImpl(contents, pageable, totalCount)
    }
}