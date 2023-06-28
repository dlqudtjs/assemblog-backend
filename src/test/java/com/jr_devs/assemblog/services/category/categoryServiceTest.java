package com.jr_devs.assemblog.services.category;

import com.jr_devs.assemblog.models.ResponseDto;
import com.jr_devs.assemblog.repositoryes.JpaCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class categoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리 생성")
    public void createCategory() {
        // given
        String title = "test_category";

        // when
        ResponseDto responseDto = categoryService.createCategory(title);

        // then
        assertThat(responseDto.getMessage()).isEqualTo("Success create category");
    }

    @Test
    @DisplayName("카테고리 이름 중복 검사")
    public void DuplicateCategoryTitleCheck() {
        // given
        String title = "test_category";

        // when
        categoryService.createCategory(title);
        ResponseDto responseDto = categoryService.createCategory(title);

        // then
        assertThat(responseDto.getMessage()).isEqualTo("Duplicate category title");
    }
}
