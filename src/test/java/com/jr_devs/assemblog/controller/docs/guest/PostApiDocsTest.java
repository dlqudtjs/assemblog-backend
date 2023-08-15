package com.jr_devs.assemblog.controller.docs.guest;

import com.jr_devs.assemblog.controller.PostController;
import com.jr_devs.assemblog.controller.docs.RestDocumentSupport;
import com.jr_devs.assemblog.model.post.PostListResponse;
import com.jr_devs.assemblog.service.post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
public class PostApiDocsTest extends RestDocumentSupport {

    @MockBean
    private PostService postService;

    @Test
    public void readPostList() throws Exception {
        // when & then
        mockMvc.perform(get("/lists/posts")
                        .param("currentPage", "1")
                        .param("pageSize", "10")
                        .param("order", "created_at")
                        .param("orderType", "desc")
                        .param("boardTitle", "자유게시판")
                        .param("searchWord", "검색어")
                        .param("tagName", "태그")
                        .header("Authorization", "Bearer undefined"))
                .andDo(document("{class-name}/{method-name}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                ),
                                queryParameters(
                                        parameterWithName("currentPage").description("현재 페이지"),
                                        parameterWithName("pageSize").description("페이지 당 게시글 수"),
                                        parameterWithName("order").description("정렬 기준"),
                                        parameterWithName("orderType").description("정렬 방식"),
                                        parameterWithName("boardTitle").description("게시판 이름"),
                                        parameterWithName("searchWord").description("검색어"),
                                        parameterWithName("tagName").description("태그 이름")
                                ),
                                responseFields(
                                        fieldWithPath("[].postId").description("게시글 ID"),
                                        fieldWithPath("[].username").description("유저 이름"),
                                        fieldWithPath("[].profileImage").description("유저 프로필 이미지"),
                                        fieldWithPath("[].title").description("게시글 제목"),
                                        fieldWithPath("[].thumbnail").description("게시글 썸네일"),
                                        fieldWithPath("[].preview").description("게시글 미리보기"),
                                        fieldWithPath("[].createdAt").description("게시글 생성일"),
                                        fieldWithPath("[].updatedAt").description("게시글 수정일"),
                                        fieldWithPath("[].categoryTitle").description("카테고리 이름"),
                                        fieldWithPath("[].boardTitle").description("게시판 이름"),
                                        fieldWithPath("[].viewCount").description("조회수"),
                                        fieldWithPath("[].commentCount").description("댓글 수"),
                                        fieldWithPath("totalPage").description("총 페이지 수"),
                                        fieldWithPath("currentPage").description("현재 페이지")
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}

