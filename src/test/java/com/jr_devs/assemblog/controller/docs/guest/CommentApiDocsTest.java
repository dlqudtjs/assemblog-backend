package com.jr_devs.assemblog.controller.docs.guest;

import com.jr_devs.assemblog.controller.CommentController;
import com.jr_devs.assemblog.controller.docs.RestDocumentSupport;
import com.jr_devs.assemblog.model.comment.CommentRequest;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.service.comment.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
public class CommentApiDocsTest extends RestDocumentSupport {

    @MockBean
    private CommentService commentService;

    @Test
    public void createComment() throws Exception {
        // given
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .nickname("test_nickname")
                .content("test_content")
                .parentCommentId(0L)
                .password("1234")
                .build();

        given(commentService.createComment(any(), eq("Bearer undefined"))).willReturn(ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message("댓글이 등록되었습니다.")
                .build());

        // when & then
        mockMvc.perform(post("/comments")
                        .header("Authorization", "Bearer undefined")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequest)))
                .andDo(document("{class-name}/{method-name}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                ),
                                requestFields(
                                        fieldWithPath("postId").description("게시글 ID"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("content").description("댓글 내용"),
                                        fieldWithPath("parentCommentId").description("부모 댓글 ID"),
                                        fieldWithPath("password").description("비밀번호")
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").description("응답 상태 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("data").description("응답 데이터")
                                )
                        )
                )
                .andExpect(status().isOk());

    }
}
