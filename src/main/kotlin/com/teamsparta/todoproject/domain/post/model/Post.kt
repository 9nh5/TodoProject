package com.teamsparta.todoproject.domain.post.model

import com.teamsparta.todoproject.domain.comment.model.Comment
import com.teamsparta.todoproject.domain.comment.model.toResponse
import com.teamsparta.todoproject.domain.post.dto.PostResponse
import com.teamsparta.todoproject.domain.user.model.User
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "post")
class Post(

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "status", nullable = false)
    var status: Boolean = false,//기본 상태는 false로 지정, 완료하는거 실행했을 때만 true로 변경

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var comments: MutableList<Comment> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun createComment(comment: Comment) {
        comments.add(comment)
    }

    fun deleteComment(comment: Comment) {
        comments.remove(comment)
    }

}

fun Post.toResponse(): PostResponse {
    return PostResponse(
        id = id!!,
        title = title,
        content = content,
        createdAt = createdAt,
        status = status,
        user_id = user.id!!,//사용자 아이디 불러오는거
        name = user.profile.name,//사용자 이름 불러오는거
        comments = comments.map { it.toResponse() }//포스트 조회했을 때 관련 댓글 목록도 같이 나오도록함
    )
}