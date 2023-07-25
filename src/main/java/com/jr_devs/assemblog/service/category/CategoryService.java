package com.jr_devs.assemblog.service.category;

import com.jr_devs.assemblog.model.category.Category;
import com.jr_devs.assemblog.model.category.CategoryRequest;
import com.jr_devs.assemblog.model.category.CategoryResponse;
import com.jr_devs.assemblog.model.dto.ResponseDto;

import java.util.List;

public interface CategoryService {

    ResponseDto createCategory(CategoryRequest categoryRequest);

    List<Category> readAllCategories();

    List<CategoryResponse> readAllCategoriesAndBoards();

    ResponseDto updateCategory(CategoryRequest categoryDtoList);

    ResponseDto deleteCategory(Long categoryId);
}
