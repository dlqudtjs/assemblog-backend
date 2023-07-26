package com.jr_devs.assemblog.service.guestbook;

import com.jr_devs.assemblog.model.comment.CommentListResponse;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.guestbook.Guestbook;
import com.jr_devs.assemblog.model.guestbook.GuestbookRequest;
import com.jr_devs.assemblog.repository.JpaGuestbookRepository;
import com.jr_devs.assemblog.repository.JpaUserRepository;
import com.jr_devs.assemblog.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GuestbookServiceImpl implements GuestbookService {

    private final JpaGuestbookRepository guestbookRepository;
    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public ResponseDto createGuestbook(GuestbookRequest guestbookRequest, String token) {
        boolean isAdmin;
        Long writerId = 0L;

        // 비회원일 경우 ("undefined" 으로 넘어옴, 나머지는 토큰으로 분류)
        if ((token.startsWith("Bearer ")) && (token.substring(7).equals("undefined"))) {
            guestbookRequest.setPassword(passwordEncoder.encode(guestbookRequest.getPassword()));
            isAdmin = false;
            writerId = 0L;
        } else {
            // 토큰 검증
            if (!(token.startsWith("Bearer ")) || !(jwtProvider.validateToken(token.substring(7)))) {
                return createResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid Token", null);
            }

            writerId = userRepository.findByEmail(jwtProvider.getEmailFromToken(token.substring(7))).get().getId();
            guestbookRequest.setPassword(null);
            isAdmin = true;
        }

        // 부모 댓글이 존재하지 않으면 에러
        if (guestbookRequest.getParentCommentId() != 0 && guestbookRepository.findById(guestbookRequest.getParentCommentId()).isEmpty()) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Parent Comment Not found", null);
        }

        guestbookRepository.save(Guestbook.builder()
                .writerId(writerId)
                .nickname(guestbookRequest.getNickname())
                .content(guestbookRequest.getContent())
                .password(guestbookRequest.getPassword())
                .parentCommentId(guestbookRequest.getParentCommentId())
                .likeState(false)
                .isWriter(isAdmin)
                .deleted(false)
                .build());

        return createResponse(HttpStatus.OK.value(), "Success to create guestbook", null);
    }

    @Override
    public ResponseDto readGuestbookList() {
        List<Guestbook> guestbookListResponse = guestbookRepository.findAll();

        ArrayList<CommentListResponse> commentListResponse = new ArrayList<>();

        for (Guestbook guestbook : guestbookListResponse) {
            commentListResponse.add(CommentListResponse.builder()
                    .id(guestbook.getId())
                    .writerId(guestbook.getWriterId())
                    .nickname(guestbook.getNickname())
                    .content(guestbook.getContent())
                    .parentCommentId(guestbook.getParentCommentId())
                    .createdAt(guestbook.getCreatedAt())
                    .likeState(guestbook.isLikeState())
                    .isWriter(guestbook.isWriter())
                    .deleted(guestbook.isDeleted())
                    .build());
        }

        return createResponse(HttpStatus.OK.value(), "Success to read guestbook list", commentListResponse);
    }

    @Override
    @Transactional
    public ResponseDto deleteGuestbook(Long guestbookId, String password, String token) {
        Guestbook guestbook = guestbookRepository.findById(guestbookId).orElse(null);

        if (guestbook == null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Guestbook not found", null);
        }

        // 비회원은 항상 "Bearer undefined" 로 넘어옴, 따라서 비밀번호 검사를 실시
        if ((token.startsWith("Bearer ")) && (token.substring(7).equals("undefined"))) {
            if (!passwordEncoder.matches(password, guestbook.getPassword())) {
                return createResponse(HttpStatus.BAD_REQUEST.value(), "Password is incorrect", null);
            }
        }
        // 비회원이 아닐 시 토큰 검증 후 비밀번호 검사 x
        else {
            if (!(token.startsWith("Bearer ")) || !(jwtProvider.validateToken(token.substring(7)))) {
                return createResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid Token", null);
            }
        }

        // 자신이 부모 댓글이라면
        if (guestbook.getParentCommentId() == 0) {
            // 자신 댓글에 대댓글이 있다면
            if (guestbookRepository.countAllByParentCommentId(guestbookId) > 0) {
                guestbook.setNickname("삭제된 사용자");
                guestbook.setContent("삭제된 댓글입니다.");
                guestbook.setDeleted(true);
            } else {
                guestbookRepository.deleteById(guestbookId);
            }
        }
        // 자신이 대댓글 이라면 바로 삭제
        else {
            guestbookRepository.deleteById(guestbookId);

            // db 적용
            guestbookRepository.flush();

            // 자신의 부모 댓글이 삭제 돼었고, 대댓글이 없다면 부모 댓글도 삭제
            if (guestbookRepository.findById(guestbook.getParentCommentId()).get().isDeleted() && guestbookRepository.countAllByParentCommentId(guestbook.getParentCommentId()) == 0) {
                guestbookRepository.deleteById(guestbook.getParentCommentId());
            }
        }

        return createResponse(HttpStatus.OK.value(), "Success to delete comment", null);
    }

    @Transactional
    @Override
    public ResponseDto likeGuestbook(Long guestbookId) {
        Guestbook guestbook = guestbookRepository.findById(guestbookId).orElse(null);

        if (guestbook == null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Guestbook not found", null);
        }

        guestbook.setLikeState(!guestbook.isLikeState());

        return createResponse(HttpStatus.OK.value(), "Success like comment", null);
    }

    private ResponseDto createResponse(int statusCode, String message, Object data) {
        return ResponseDto.builder()
                .statusCode(statusCode)
                .message(message)
                .data(data)
                .build();
    }
}
