package com.example.demo.controller;

import com.example.demo.dto.CategoryListResDto;
import com.example.demo.dto.CategoryReqDto;
import com.example.demo.dto.CategoryResDto;
import com.example.demo.dto.SubCategoryReqDto;
import com.example.demo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ledger")
@RequiredArgsConstructor

@Tag(name = "카테고리", description = "카테고리 API")
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 추가
    @Operation(summary = "카테고리 추가", description = "가계부 내 상위 카테고리를 생성한다.")
    @PostMapping("/{ledgerId}/category")
    public ResponseEntity<Long> createCategory(@PathVariable Long ledgerId, @Valid @RequestBody CategoryReqDto categoryReqDto){
        Long createCategoryId = categoryService.save(ledgerId, categoryReqDto);
        return ResponseEntity.ok(createCategoryId);
    }

    // 서브 카테고리 추가
    @Operation(summary = "서브 카테고리 추가", description = "가계부에 생성된 상위 카테고리 내 하위 카테고리를 생성한다. {categoryId}는 상위 카테고리 id를 의미한다. type을 따로 설정하지 않고 상위 카테고리의 type으로 자동 설정한다.")
    @PostMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<Long> createCategory(@PathVariable Long ledgerId, @PathVariable Long categoryId, @Valid @RequestBody SubCategoryReqDto categoryReqDto){
        Long createCategoryId = categoryService.save(ledgerId, categoryId, categoryReqDto);
        return ResponseEntity.ok(createCategoryId);
    }

    // 카테고리 상세 조회
    @Operation(summary = "카테고리 상세 조회", description = "가계부 내 카테고리 정보를 상세 조회한다. 상위 카테고리의 경우 하위 카테고리 리스트도 함께 조회한다.")
    @GetMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<CategoryResDto> getCategory(@PathVariable Long categoryId){
        CategoryResDto category = categoryService.find(categoryId);
        return ResponseEntity.ok(category);
    }

    // 가계부 별 카테고리 목록 조회(서브 카테고리 제외)
    @Operation(summary = "카테고리 목록 조회", description = "가계부 내 카테고리 목록를 조회한다. 하위 카테고리는 제외한다.")
    @GetMapping("/{ledgerId}/category")
    public ResponseEntity<List<CategoryListResDto>> getCategoryList(@PathVariable Long ledgerId){
        List<CategoryListResDto> categoryList = categoryService.findAll(ledgerId);
        return ResponseEntity.ok(categoryList);
    }

    // 카테고리 수정
    @Operation(summary = "카테고리 수정", description = "가계부 내 카테고리 정보를 수정한다.")
    @PutMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<Long> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryReqDto categoryReqDto){
        Long updatedCategoryId = categoryService.update(categoryId, categoryReqDto);
        return ResponseEntity.ok(updatedCategoryId);
    }

    // 카테고리 삭제
    @Operation(summary = "카테고리 삭제", description = "가계부 내 카테고리를 삭제한다.")
    @DeleteMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        categoryService.delete(categoryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}