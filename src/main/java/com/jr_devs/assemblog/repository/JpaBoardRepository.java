package com.jr_devs.assemblog.repository;

import com.jr_devs.assemblog.model.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaBoardRepository extends JpaRepository<Board, Long> {

    Board save(Board board);

    List<Board> findAllByParentIdOrderByOrderNum(Long parentId);

    Optional<Board> findByTitle(String title);

    List<Board> findAllByTitle(String title);

    Boolean existsByTitle(String title);

    Long countBy();

    int countByParentId(Long parentId);
}
