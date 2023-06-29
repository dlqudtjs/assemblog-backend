package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.CategoryDto;
import com.jr_devs.assemblog.services.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/api/categories")
    public ResponseEntity<String> createCategory(@RequestBody CategoryDto categoryDto) {
        try {
            return ResponseEntity.ok(categoryService.createCategory(categoryDto).getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 전체 카테고리 조회
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> readAllCategoriesAndBoards() {
        try {
            return ResponseEntity.ok(categoryService.readAllCategoriesAndBoards());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/api/categories")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDto categoryDto) {
        try {
            return ResponseEntity.ok(categoryService.updateCategory(categoryDto).getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId) {
        try {
            return ResponseEntity.ok(categoryService.deleteCategory(categoryId).getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
