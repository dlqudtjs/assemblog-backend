package com.jr_devs.assemblog.service.board;

import com.jr_devs.assemblog.model.board.Board;
import com.jr_devs.assemblog.model.board.BoardDto;
import com.jr_devs.assemblog.model.dto.ResponseDto;

import java.util.List;

public interface BoardService {

    ResponseDto createBoard(BoardDto boardDto);

    ResponseDto updateBoard(List<BoardDto> boardDtoList);

    ResponseDto deleteBoard(Long boardId);

    List<Board> readAllByParentId(Long id);

    String getCategoryTitleByBoardId(Long boardId);

    String getBoardTitle(Long id);
}
