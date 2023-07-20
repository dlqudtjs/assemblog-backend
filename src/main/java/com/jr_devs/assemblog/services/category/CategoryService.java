package com.jr_devs.assemblog.services.category;

import com.jr_devs.assemblog.models.category.Category;
import com.jr_devs.assemblog.models.category.CategoryDto;
import com.jr_devs.assemblog.models.dto.ResponseDto;

import java.util.List;

public interface CategoryService {

    ResponseDto createCategory(CategoryDto categoryDto);

    List<Category> readAllCategories();

    List<CategoryDto> readAllCategoriesAndBoards();

    ResponseDto updateCategory(List<CategoryDto> categoryDtoList);

    ResponseDto deleteCategory(Long categoryId);

    String getCategoryTitle(Long id);
}
