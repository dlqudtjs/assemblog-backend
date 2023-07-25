package com.jr_devs.assemblog.service.board;

import com.jr_devs.assemblog.model.board.Board;
import com.jr_devs.assemblog.model.category.Category;
import com.jr_devs.assemblog.model.board.BoardRequest;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.repository.JpaBoardRepository;
import com.jr_devs.assemblog.repository.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final JpaBoardRepository boardRepository;
    private final JpaCategoryRepository categoryRepository;

    @Override
    public ResponseDto createBoard(BoardRequest boardRequest) {
        if (boardRepository.existsByTitle(boardRequest.getTitle())) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Duplicate board title");
        }

        int order = getBoardOrder().intValue() + 1;

        // 게시판 생성
        boardRepository.save(Board.builder()
                .parentId(boardRequest.getParentId())
                .title(boardRequest.getTitle())
                .useState(true)
                .orderNum(order)
                .build());

        // 게시판 생성 응답
        return createResponse(HttpStatus.OK.value(), "Success create board");
    }

    @Transactional
    @Override
    public ResponseDto updateBoard(List<BoardRequest> boardRequestList) {
        for (BoardRequest boardRequest : boardRequestList) {
            Board board = boardRepository.findById(boardRequest.getId()).orElse(null);

            if (board == null) {
                return createResponse(HttpStatus.BAD_REQUEST.value(), "Not exist board");
            }

            List<Board> boardList = boardRepository.findAllByTitle(boardRequest.getTitle());

            for (Board b : boardList) {
                if (!b.getId().equals(boardRequest.getId())) {
                    return createResponse(HttpStatus.BAD_REQUEST.value(), "Duplicate board title");
                }
            }

            System.out.println(boardRequest.getTitle() + " | " + boardRequest.getOrderNum());

            board.setTitle(boardRequest.getTitle());
            board.setUseState(boardRequest.isUseState());
            board.setOrderNum(boardRequest.getOrderNum());
        }

        return createResponse(HttpStatus.OK.value(), "Success update board");
    }

    @Override
    public ResponseDto deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);

        if (board == null && !board.isUseState()) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Not exist board");
        }

        boardRepository.deleteById(boardId);

        return createResponse(HttpStatus.OK.value(), "Success delete board");
    }

    @Override
    public List<Board> readAllByParentId(Long parentId) {
        return boardRepository.findAllByParentIdOrderByOrderNum(parentId);
    }

    @Override
    public String getCategoryTitleByBoardId(Long id) {
        // boardId를 통해 parentId를 찾는다.
        Long parentId = boardRepository.findById(id).orElse(null).getParentId();
        // parentId를 통해 categoryTitle 를 찾는다.
        Category category = categoryRepository.findById(parentId).orElse(null);

        if (category == null) {
            return null;
        }

        return category.getTitle();
    }

    @Override
    public String getBoardTitle(Long id) {
        Board board = boardRepository.findById(id).orElse(null);

        if (board == null) {
            return null;
        }

        return board.getTitle();
    }

    private Long getBoardOrder() {
        return boardRepository.countBy();
    }

    private ResponseDto createResponse(int value, String message) {
        return ResponseDto.builder()
                .statusCode(value)
                .message(message)
                .build();
    }
}
