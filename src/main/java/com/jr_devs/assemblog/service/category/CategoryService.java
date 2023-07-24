package com.jr_devs.assemblog.service.category;

import com.jr_devs.assemblog.model.category.Category;
import com.jr_devs.assemblog.model.category.CategoryDto;
import com.jr_devs.assemblog.model.dto.ResponseDto;

import java.util.List;

public interface CategoryService {

    ResponseDto createCategory(CategoryDto categoryDto);

    List<Category> readAllCategories();

    List<CategoryDto> readAllCategoriesAndBoards();

    ResponseDto updateCategory(List<CategoryDto> categoryDtoList);

    ResponseDto deleteCategory(Long categoryId);

    String getCategoryTitle(Long id);
}
