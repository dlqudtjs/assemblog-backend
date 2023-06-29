package com.jr_devs.assemblog.repositoryes;

import com.jr_devs.assemblog.models.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaBoardRepository extends JpaRepository<Board, Long> {

    Board save(Board board);

    List<Board> findAllByParentId(Long parentId);

    Optional<Board> findByTitle(String title);

    Long countBy();
}
