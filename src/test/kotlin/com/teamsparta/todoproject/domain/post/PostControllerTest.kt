package com.teamsparta.todoproject.domain.post

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.teamsparta.todoproject.domain.post.dto.PostResponse
import com.teamsparta.todoproject.domain.post.service.PostService
import com.teamsparta.todoproject.infra.security.jwt.JwtPlugin
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val jwtPlugin: JwtPlugin
) : DescribeSpec({extensions(SpringExtension)

    afterContainer {
        clearAllMocks() }

    val postService = mockk<PostService>()

    describe("GET /posts/{id}") {
        context("존재하는 ID 요청할때 ") {
            it("200 status code 응답") {
                val postId = 1L

                every { postService.getPostById(any()) }returns PostResponse(
                    id = postId,
                    title = "테스트 타이틀",
                    content = "test content",
                    createdAt = LocalDateTime.now(),
                    status = true,
                    user_id = 1,
                    comments = mutableListOf()
                )

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "12",
                    role = "User"
                )
                val result = mockMvc.perform(
                    get("/posts/$postId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()

                result.response.status shouldBe 200

                val responseDto = jacksonObjectMapper().readValue(
                    result.response.contentAsString,
                    PostResponse::class.java
                )

                responseDto.id shouldBe postId
            }
        }
    }
})