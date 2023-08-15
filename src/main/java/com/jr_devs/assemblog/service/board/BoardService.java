package com.jr_devs.assemblog.service.board;

import com.jr_devs.assemblog.model.board.Board;
import com.jr_devs.assemblog.model.board.BoardRequest;
import com.jr_devs.assemblog.model.dto.ResponseDto;

import java.util.List;

public interface BoardService {

    ResponseDto createBoard(BoardRequest boardRequest);

    ResponseDto updateBoard(List<BoardRequest> boardRequestList);

    ResponseDto deleteBoard(Long boardId);

    List<Board> readAllByParentId(Long id);

    String getCategoryTitleByBoardId(Long boardId);

    String getBoardTitle(Long id);

    int getPostCount(Long boardId);
}
