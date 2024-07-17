package com.example.demo.controller;

import com.example.demo.dto.CategoryDto;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ledger")
@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 추가
    @PostMapping("/{ledgerId}/category")
    public ResponseEntity<Long> createCategory(@PathVariable Long ledgerId, @Valid @RequestBody CategoryDto categoryDto){
        Long createCategoryId = categoryService.save(ledgerId, categoryDto);
        return ResponseEntity.ok(createCategoryId);
    }

    // 서브 카테고리 추가
    @PostMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<Long> createCategory(@PathVariable Long ledgerId, @PathVariable Long categoryId, @Valid @RequestBody CategoryDto categoryDto){
        Long createCategoryId = categoryService.save(ledgerId, categoryId, categoryDto);
        return ResponseEntity.ok(createCategoryId);
    }

    // 카테고리 상세 조회
    @GetMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId){
        CategoryDto category = categoryService.find(categoryId);
        return ResponseEntity.ok(category);
    }

    // 가계부 별 카테고리 목록 조회(서브 카테고리 제외)
    @GetMapping("/{ledgerId}/category")
    public ResponseEntity<List<CategoryDto>> getCategoryList(@PathVariable Long ledgerId){
        List<CategoryDto> categoryList = categoryService.findAll(ledgerId);
        return ResponseEntity.ok(categoryList);
    }

//    // 가계부 별 서브 카테고리 목록 조회
//    @GetMapping("/{ledgerId}/category/{categoryId}")
//    public ResponseEntity<List<CategoryDto>> getSubCategoryList(@PathVariable Long categoryId){
//        List<CategoryDto> categoryList = categoryService.findAllSub(categoryId);
//        return ResponseEntity.ok(categoryList);
//    }

    // 카테고리 수정
    @PutMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<Long> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto){
        Long updatedCategoryId = categoryService.update(categoryId, categoryDto);
        return ResponseEntity.ok(updatedCategoryId);
    }

    // 카테고리 삭제
    @DeleteMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        categoryService.delete(categoryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}