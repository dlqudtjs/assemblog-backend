package com.jr_devs.assemblog.service.category;

import com.jr_devs.assemblog.model.category.Category;
import com.jr_devs.assemblog.model.category.CategoryRequest;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.repository.JpaCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class categoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JpaCategoryRepository categoryRepository;


}
