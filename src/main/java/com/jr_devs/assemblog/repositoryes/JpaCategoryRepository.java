package com.jr_devs.assemblog.repositoryes;

import com.jr_devs.assemblog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCategoryRepository extends JpaRepository<Category, Long> {

    Category save(Category category);

    Optional<Category> findById(Long id);

    void deleteById(Long id);

    Long countBy();

    Boolean existsByTitle(String title);
}
