package com.jr_devs.assemblog.services.board;

import com.jr_devs.assemblog.models.Board;
import com.jr_devs.assemblog.models.BoardDto;
import com.jr_devs.assemblog.models.ResponseDto;
import com.jr_devs.assemblog.repositoryes.JpaBoardRepository;
import com.jr_devs.assemblog.services.boards.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private JpaBoardRepository boardRepository;

    @Test
    @DisplayName("게시판 생성")
    public void createBoard() {
        // when
        ResponseDto responseDto = boardService.createBoard(BoardDto.builder()
                .parentId(1L)
                .title("test_board")
                .build());

        // then
        assertThat(responseDto.getMessage()).isEqualTo("Success create board");
    }

    @Test
    @DisplayName("같은 부모 카테고리내의 게시판 이름 중복 검사")
    public void DuplicateBoardTitleCheck() {
        // given
        boardService.createBoard(BoardDto.builder()
                .parentId(1L)
                .title("test_board")
                .build());

        // when
        ResponseDto responseDto = boardService.createBoard(BoardDto.builder()
                .parentId(1L)
                .title("test_board")
                .build());

        // then
        assertThat(responseDto.getMessage()).isEqualTo("Duplicate board title");
    }

    @Test
    @DisplayName("게시판 title, order, 숨김 수정")
    public void updateBoardTest() {
        // given
        boardService.createBoard(BoardDto.builder()
                .parentId(1L)
                .title("test_board")
                .build());
        Board testBoard = boardRepository.findByTitle("test_board").get();

        // when
        testBoard.setTitle("test_board_update");
        testBoard.setUseState(false);
        testBoard.setOrderNum(Integer.MAX_VALUE);
        testBoard.setParentId(Long.MAX_VALUE);

        // then
        assertThat(testBoard.getTitle()).isEqualTo("test_board_update");
        assertThat(testBoard.isUseState()).isFalse();
        assertThat(testBoard.getOrderNum()).isEqualTo(Integer.MAX_VALUE);
        assertThat(testBoard.getParentId()).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("게시판 삭제")
    public void deleteBoardTest() {
        // given
        boardService.createBoard(BoardDto.builder()
                .parentId(1L)
                .title("test_board")
                .build());
        Board testBoard = boardRepository.findByTitle("test_board").get();

        // when
        boardService.deleteBoard(testBoard.getId());

        // then
        assertThat(boardRepository.findByTitle("test_board")).isEmpty();
    }
}
