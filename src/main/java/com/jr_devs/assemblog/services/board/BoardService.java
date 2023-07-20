package com.jr_devs.assemblog.services.board;

import com.jr_devs.assemblog.models.board.Board;
import com.jr_devs.assemblog.models.board.BoardDto;
import com.jr_devs.assemblog.models.dto.ResponseDto;

import java.util.List;

public interface BoardService {

    ResponseDto createBoard(BoardDto boardDto);

    ResponseDto updateBoard(List<BoardDto> boardDtoList);

    ResponseDto deleteBoard(Long boardId);

    List<Board> readAllByParentId(Long id);

    String getCategoryTitleByBoardId(Long boardId);

    String getBoardTitle(Long id);
}
