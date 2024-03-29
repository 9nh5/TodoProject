package com.teamsparta.todoproject.domain.post.service

//import com.teamsparta.todoproject.domain.post.model.PostStatus
import com.teamsparta.todoproject.domain.exception.PostNotFoundException
import com.teamsparta.todoproject.domain.exception.dto.NotAuthorizationException
import com.teamsparta.todoproject.domain.post.dto.CreatePostRequest
import com.teamsparta.todoproject.domain.post.dto.PostResponse
import com.teamsparta.todoproject.domain.post.dto.UpdatePostRequest
import com.teamsparta.todoproject.domain.post.dto.UpdatePostStatusRequest
import com.teamsparta.todoproject.domain.post.model.Post
import com.teamsparta.todoproject.domain.post.model.toResponse
import com.teamsparta.todoproject.domain.post.repository.PostRepository
import com.teamsparta.todoproject.domain.user.exception.UserNotFoundException
import com.teamsparta.todoproject.domain.user.repository.UserRepository
import com.teamsparta.todoproject.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder
): PostService{

    override fun searchPostListByTitle(title: String): List<PostResponse>? {//제목으로 검색하는거
        return postRepository.searchPostListByTitle(title).map { it.toResponse() }
    }

    override fun searchPostListByName(name: String): List<PostResponse>? {//사용자 이름으로 검색하는거
        return postRepository.searchPostListByName(name).map { it.toResponse() }
    }

    override fun getAllPostList(): List<PostResponse> {//싹 다 조회
        return postRepository.findAll().map {it.toResponse()}
    }

    override fun getPaginatedPostList(pageable: Pageable): Page<PostResponse> {//목록 설정하는거
        return postRepository.findByPageable(pageable).map { it.toResponse() }
    }

//    @StopWatch
    @Transactional
    override fun getPostById(postId: Long): PostResponse {//게시글 아이디로 단건조회
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException("Post", postId)
        return post.toResponse()
    }

    @Transactional
    override fun createPost(userPrincipal: UserPrincipal, request: CreatePostRequest): PostResponse {//userPrincipal 추가
        val user = userRepository.findByIdOrNull(userPrincipal.id)?: throw UserNotFoundException("User", null)//로그인한 사용자 아이디 확인
        return postRepository.save<Post?>(
            Post(
                user = user,
                title = request.title,
                content = request.content
            )
        ).toResponse()
    }

    @Transactional
    override fun updatePost(userPrincipal: UserPrincipal, postId: Long, request: UpdatePostRequest): PostResponse {//userPrincipal 추가
        userRepository.findByIdOrNull(userPrincipal.id)?: throw UserNotFoundException("User", null)//로그인한 사용자 아이디 확인
        val post = postRepository.findByIdOrNull(postId)?: throw PostNotFoundException("Post", postId)
        if(post.user.id != userPrincipal.id) throw NotAuthorizationException()//사용자 일치하는지 확인
//        val post = postRepository.findByUserIdAndId(userId=userPrincipal.id, postId)?: throw PostNotFoundException("Post", postId) //사용자 권한 확인 2
        val (title, content) = request


        post.title = title
        post.content = content

        return postRepository.save(post).toResponse()
    }

    @Transactional
    override fun updatePostStatus(userPrincipal: UserPrincipal, postId: Long, request: UpdatePostStatusRequest): PostResponse {//userPrincipal 추가
        userRepository.findByIdOrNull(userPrincipal.id)?: throw UserNotFoundException("User", null)//로그인한 사용자 아이디 확인
        val post = postRepository.findByIdOrNull(postId)?: throw PostNotFoundException("Post", postId)
        if(post.user.id != userPrincipal.id) throw NotAuthorizationException()//사용자 일치하는지 확인

        post.status = true

        return postRepository.save(post).toResponse()
    }


    @Transactional
    override fun deletePost(userPrincipal: UserPrincipal, postId: Long) {//userPrincipal 추가
        userRepository.findByIdOrNull(userPrincipal.id)?: throw UserNotFoundException("User", null)//로그인한 사용자 아이디 확인
        val post = postRepository.findByIdOrNull(postId)?: throw PostNotFoundException("Post", postId)
        if(post.user.id != userPrincipal.id) throw NotAuthorizationException()//사용자 일치하는지 확인
        postRepository.delete(post)

    }
}