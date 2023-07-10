package com.jr_devs.assemblog.services.board;

import com.jr_devs.assemblog.models.Board;
import com.jr_devs.assemblog.models.dtos.BoardDto;
import com.jr_devs.assemblog.models.dtos.ResponseDto;

import java.util.List;

public interface BoardService {

    ResponseDto createBoard(BoardDto boardDto);

    ResponseDto updateBoard(BoardDto boardDto);

    ResponseDto deleteBoard(Long boardId);

    List<Board> readAllByParentId(Long id);

    String getCategoryTitleByBoardId(Long boardId);

    String getBoardTitle(Long id);
}
