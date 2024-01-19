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
class PostRepositoryImpl: QueryDslSupport(), CustomPostRepository {//쿼리문

    private  val post = QPost.post

    override fun searchPostListByTitle(title: String) : List<Post> {//제목으로 검색하는 쿼리
        return queryFactory.selectFrom(post)
            .where(post.title.containsIgnoreCase(title))
            .fetch()
    }

    override fun searchPostListByName(name: String): List<Post> {//사용자 이름으로 검색하는 쿼리
        return queryFactory.selectFrom(post)
            .where(post.user.profile.name.containsIgnoreCase(name))//post의 user에 있는 profile의 이름을 가져옴
            .fetch()
    }

    override fun findByPageable(pageable: Pageable): Page<Post> {//목록 설정해서 조회하는 쿼리
        val totalCount = queryFactory.select(post.count()).from(post).fetchOne()?:0L

        val comment = QComment.comment
        val query = queryFactory.selectFrom(post)
            .leftJoin(post.comments, comment)
            .fetchJoin()
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        if (pageable.sort.isSorted) {//어떤 정렬을 사용할지, 기본은 createdAt생성일이며 내림차순, 나머지는 설정한 대로 조회
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