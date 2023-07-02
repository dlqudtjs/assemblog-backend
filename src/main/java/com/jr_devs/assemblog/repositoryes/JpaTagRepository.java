package com.jr_devs.assemblog.repositoryes;

import com.jr_devs.assemblog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTagRepository extends JpaRepository<Tag, Long> {

    Tag save(Tag tag);

    Tag findByName(String name);

    void deleteById(Long id);

    // 이름 순으로 정렬하여 모든 태그를 가져온다.
    List<Tag> findAllByOrderByNameAsc();
}
