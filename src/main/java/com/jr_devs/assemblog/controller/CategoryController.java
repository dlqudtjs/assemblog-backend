package com.jr_devs.assemblog.controller;

import com.jr_devs.assemblog.model.category.CategoryRequest;
import com.jr_devs.assemblog.model.category.CategoryResponse;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/api/categories")
    public ResponseEntity<String> createCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            ResponseDto responseDto = categoryService.createCategory(categoryRequest);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 전체 카테고리 조회
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> readAllCategoriesAndBoards() {
        try {
            return ResponseEntity.ok(categoryService.readAllCategoriesAndBoards());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/api/categories")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryRequest categoryRequestList) {
        try {
            ResponseDto responseDto = categoryService.updateCategory(categoryRequestList);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            ResponseDto responseDto = categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
