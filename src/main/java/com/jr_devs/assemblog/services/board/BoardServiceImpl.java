package com.jr_devs.assemblog.services.board;

import com.jr_devs.assemblog.models.Board;
import com.jr_devs.assemblog.models.Category;
import com.jr_devs.assemblog.models.dtos.BoardDto;
import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.repositoryes.JpaBoardRepository;
import com.jr_devs.assemblog.repositoryes.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final JpaBoardRepository boardRepository;
    private final JpaCategoryRepository categoryRepository;

    @Override
    public ResponseDto createBoard(BoardDto boardDto) {
        if (!checkDuplicate(boardDto.getParentId(), boardDto.getTitle())) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Duplicate board title");
        }

        int order = getBoardOrder().intValue() + 1;

        // 게시판 생성
        boardRepository.save(Board.builder()
                .parentId(boardDto.getParentId())
                .title(boardDto.getTitle())
                .useState(true)
                .orderNum(order)
                .build());

        // 게시판 생성 응답
        return createResponse(HttpStatus.OK.value(), "Success create board");
    }

    @Override
    public ResponseDto updateBoard(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getId()).orElse(null);

        if (board == null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Not exist board");
        }

        List<Board> boardList = boardRepository.findAllByTitle(boardDto.getTitle());
        for (Board b : boardList) {
            if (!b.getId().equals(boardDto.getId())) {
                return createResponse(HttpStatus.BAD_REQUEST.value(), "Duplicate board title");
            }
        }

        board.setTitle(boardDto.getTitle());
        board.setUseState(boardDto.isUseState());
        board.setOrderNum(boardDto.getOrderNum());

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
        return boardRepository.findAllByParentId(parentId);
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

    // 카테고리명 중복 체크
    private Boolean checkDuplicate(Long parentId, String title) {
        List<Board> boards = readAllByParentId(parentId);
        // parentId로 찾은 board 안에 중복된 title 이 있는지 확인
        for (Board board : boards) {
            if (board.getTitle().equals(title)) {
                return false;
            }
        }

        return true;
    }

    private ResponseDto createResponse(int value, String message) {
        return ResponseDto.builder()
                .statusCode(value)
                .message(message)
                .build();
    }
}