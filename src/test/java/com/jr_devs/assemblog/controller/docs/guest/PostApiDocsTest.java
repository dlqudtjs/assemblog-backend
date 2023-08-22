package com.jr_devs.assemblog.controller.docs.guest;

import com.jr_devs.assemblog.controller.PostController;
import com.jr_devs.assemblog.controller.docs.RestDocumentSupport;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.post.PostListResponse;
import com.jr_devs.assemblog.model.post.PostListResponseDto;
import com.jr_devs.assemblog.service.post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
        // given
        List<PostListResponseDto> postList = new ArrayList<>();
        postList.add(PostListResponseDto.builder()
                .postId(1L)
                .username("test_username")
                .profileImage("test_profile_image")
                .title("test_title")
                .thumbnail("test_thumbnail")
                .preview("test_preview")
                .createdAt(new Date())
                .updatedAt(new Date())
                .categoryTitle("test_category_title")
                .boardTitle("test_board_title")
                .viewCount(1)
                .commentCount(1)
                .build());

        PostListResponse postListResponse = PostListResponse.builder()
                .totalPage(1)
                .currentPage(1)
                .postList(postList)
                .build();

        given(postService.readPostList(any(Integer.class), any(Integer.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class)))
                .willReturn(ResponseDto.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success read post list")
                        .data(postListResponse)
                        .build());

        // when & then
        mockMvc.perform(get("/lists/posts")
                        .param("currentPage", "1")
                        .param("pageSize", "10")
                        .param("order", "created_at")
                        .param("orderType", "desc")
                        .param("boardTitle", "자유게시판")
                        .param("searchWord", "검색어")
                        .param("tagName", "태그")
                        .content(objectMapper.writeValueAsString(postListResponse)))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
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
                                        fieldWithPath("totalPage").description("총 페이지 수"),
                                        fieldWithPath("currentPage").description("현재 페이지"),
                                        fieldWithPath("postList[].postId").description("게시글 ID"),
                                        fieldWithPath("postList[].username").description("유저 이름"),
                                        fieldWithPath("postList[].profileImage").description("유저 프로필 이미지"),
                                        fieldWithPath("postList[].title").description("게시글 제목"),
                                        fieldWithPath("postList[].thumbnail").description("게시글 썸네일"),
                                        fieldWithPath("postList[].preview").description("게시글 미리보기"),
                                        fieldWithPath("postList[].createdAt").description("게시글 생성일"),
                                        fieldWithPath("postList[].updatedAt").description("게시글 수정일"),
                                        fieldWithPath("postList[].categoryTitle").description("게시글 카테고리 이름"),
                                        fieldWithPath("postList[].boardTitle").description("게시글 게시판 이름"),
                                        fieldWithPath("postList[].viewCount").description("게시글 조회수"),
                                        fieldWithPath("postList[].commentCount").description("게시글 댓글 수")
                                )
                        )
                );
    }
}

