package com.jr_devs.assemblog.services.category;

import com.jr_devs.assemblog.models.category.Category;
import com.jr_devs.assemblog.models.category.CategoryDto;
import com.jr_devs.assemblog.models.dto.ResponseDto;
import com.jr_devs.assemblog.repositoryes.JpaCategoryRepository;
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

    @Test
    @DisplayName("카테고리 생성")
    public void createCategory() {
        // when
        ResponseDto responseDto = categoryService.createCategory(CategoryDto.builder()
                .title("test_category")
                .build());

        // then
        assertThat(responseDto.getMessage()).isEqualTo("Success create category");
    }

    @Test
    @DisplayName("카테고리 이름 중복 검사")
    public void DuplicateCategoryTitleCheck() {
        // given
        categoryService.createCategory(CategoryDto.builder()
                .title("test_category")
                .build());

        // when
        ResponseDto responseDto = categoryService.createCategory(CategoryDto.builder()
                .title("test_category")
                .build());

        // then
        assertThat(responseDto.getMessage()).isEqualTo("Duplicate category title");
    }

    @Test
    @DisplayName("카테고리 title, order, 숨김 수정")
    public void updateCategoryTest() {
        // given
        categoryService.createCategory(CategoryDto.builder()
                .title("test_category")
                .build());
        Category testCategory = categoryRepository.findByTitle("test_category").get();

        // when
        testCategory.setTitle("update_category");
        testCategory.setUseState(false);
        testCategory.setOrderNum(Integer.MAX_VALUE);

        // then
        assertThat(testCategory.getTitle()).isEqualTo("update_category");
        assertThat(testCategory.isUseState()).isFalse();
        assertThat(testCategory.getOrderNum()).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("카테고리 삭제")
    public void deleteCategoryTest() {
        // given
        categoryService.createCategory(CategoryDto.builder()
                .title("test_category")
                .build());
        Category testCategory = categoryRepository.findByTitle("test_category").get();

        // when
        categoryService.deleteCategory(testCategory.getId());

        // then
        assertThat(categoryRepository.findByTitle("test_category")).isEmpty();
    }
}
