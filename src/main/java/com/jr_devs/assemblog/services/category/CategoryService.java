package com.jr_devs.assemblog.services.category;

import com.jr_devs.assemblog.models.Category;
import com.jr_devs.assemblog.models.CategoryDto;
import com.jr_devs.assemblog.models.ResponseDto;

import java.util.List;

public interface CategoryService {

    ResponseDto createCategory(CategoryDto categoryDto);

    List<Category> readAllCategories();

    List<CategoryDto> readAllCategoriesAndBoards();

    ResponseDto updateCategory(CategoryDto categoryDto);

    ResponseDto deleteCategory(Long categoryId);
}