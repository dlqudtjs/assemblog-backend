package com.jr_devs.assemblog.service.board;

import com.jr_devs.assemblog.model.board.Board;
import com.jr_devs.assemblog.model.category.Category;
import com.jr_devs.assemblog.model.board.BoardRequest;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.repository.JpaBoardRepository;
import com.jr_devs.assemblog.repository.JpaCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private JpaCategoryRepository categoryRepository;

    Category category;

    @BeforeEach
    public void init() {
        String categoryTitle = "test_category";
        int order = Integer.MAX_VALUE;

        category = categoryRepository.save(Category.builder()
                .title(categoryTitle)
                .useState(true)
                .orderNum(order)
                .build());
    }

    @Test
    @DisplayName("게시판 생성")
    public void createBoard() {
        // when
        ResponseDto responseDto = boardService.createBoard(BoardRequest.builder()
                .parentId(category.getId())
                .title("test_board")
                .build());

        // then
        assertThat(responseDto.getMessage()).isEqualTo("Success create board");
    }

    @Test
    @DisplayName("같은 부모 카테고리내의 게시판 이름 중복 검사")
    public void DuplicateBoardTitleCheck() {
        // given
        String boardTitle = "test_category";
        boardService.createBoard(BoardRequest.builder()
                .parentId(category.getId())
                .title(boardTitle)
                .build());

        // when
        ResponseDto responseDto = boardService.createBoard(BoardRequest.builder()
                .parentId(category.getId())
                .title(boardTitle)
                .build());

        // then
        assertThat(responseDto.getMessage()).isEqualTo("Duplicate board title");
    }

    @Test
    @DisplayName("게시판 title, order, 숨김 수정")
    public void updateBoardTest() {
        // given
        String boardTitle = "test_board";
        boardService.createBoard(BoardRequest.builder()
                .parentId(category.getId())
                .title(boardTitle)
                .build());
        Board testBoard = boardRepository.findByTitle(boardTitle).get();

        // when
        // builder 패턴을 사용하면 값이 변경되지 않음
        String updateTitle = "test_board_update";
        testBoard.setTitle(updateTitle);
        testBoard.setUseState(false);
        testBoard.setOrderNum(Integer.MAX_VALUE);
        testBoard.setParentId(Long.MAX_VALUE);

        // then
        assertThat(testBoard.getTitle()).isEqualTo(updateTitle);
        assertThat(testBoard.isUseState()).isFalse();
        assertThat(testBoard.getOrderNum()).isEqualTo(Integer.MAX_VALUE);
        assertThat(testBoard.getParentId()).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("게시판 삭제")
    public void deleteBoardTest() {
        // given
        String boardTitle = "test_board";
        boardService.createBoard(BoardRequest.builder()
                .parentId(category.getId())
                .title(boardTitle)
                .build());
        Board testBoard = boardRepository.findByTitle(boardTitle).get();

        // when
        boardService.deleteBoard(testBoard.getId());

        // then
        assertThat(boardRepository.findByTitle(boardTitle)).isEmpty();
    }
}
