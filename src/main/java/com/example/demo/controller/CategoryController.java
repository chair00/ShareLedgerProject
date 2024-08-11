package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.response.ApiResult;
import com.example.demo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ledger")
@RequiredArgsConstructor

@Tag(name = "카테고리", description = "카테고리 API")
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 추가
    @Operation(summary = "카테고리 추가", description = "가계부 내 상위 카테고리를 생성한다.")
    @ApiResponse(responseCode = "200", description = "카테고리 생성 성공 - 카테고리 id를 반환", content = @Content(schema = @Schema(implementation = ApiResult.class)))
    @PostMapping("/{ledgerId}/category")
    public ResponseEntity<ReturnIdDTO> createCategory(@PathVariable Long ledgerId, @Valid @RequestBody CategoryReqDto categoryReqDto){
        // Long createCategoryId = categoryService.save(ledgerId, categoryReqDto);
        ReturnIdDTO createdId = categoryService.save(ledgerId, categoryReqDto);
        return ResponseEntity.ok(createdId);
        //dto가 id를 반납하니까 dto에 id를 넣어야할까??? ㅈㄴ 고민되
    }

    // 서브 카테고리 추가
    @Operation(summary = "서브 카테고리 추가", description = "가계부에 생성된 상위 카테고리 내 하위 카테고리를 생성한다. {categoryId}는 상위 카테고리 id를 의미한다. type을 따로 설정하지 않고 상위 카테고리의 type으로 자동 설정한다.")
    @PostMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<ReturnIdDTO> createCategory(@PathVariable Long ledgerId, @PathVariable Long categoryId, @Valid @RequestBody SubCategoryReqDto categoryReqDto){
        ReturnIdDTO createdId = categoryService.save(ledgerId, categoryId, categoryReqDto);
        return ResponseEntity.ok(createdId);
    }

    // 카테고리 상세 조회
    @Operation(summary = "카테고리 상세 조회", description = "가계부 내 카테고리 정보를 상세 조회한다. 상위 카테고리의 경우 하위 카테고리 리스트도 함께 조회한다.")
    @ApiResponse(content = @Content(schema = @Schema(implementation = ApiResult.class)))
    @GetMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<CategoryDTO.Response> getCategory(@PathVariable Long categoryId){
        CategoryDTO.Response category = categoryService.find(categoryId);
        return ResponseEntity.ok(category);
    }

    // 가계부 별 카테고리 목록 조회(서브 카테고리 제외)
    @Operation(summary = "카테고리 목록 조회", description = "가계부 내 카테고리 목록를 조회한다. 하위 카테고리는 제외한다.")
    @GetMapping("/{ledgerId}/category")
    public ResponseEntity<List<CategoryDTO.Response>> getCategoryList(@PathVariable Long ledgerId){
        List<CategoryDTO.Response> categoryList = categoryService.findAll(ledgerId);
        return ResponseEntity.ok(categoryList);
    }

    // 카테고리 수정
    @Operation(summary = "카테고리 수정", description = "가계부 내 카테고리 정보를 수정한다.")
    @ApiResponse(responseCode = "200", description = "카테고리 수정 성공 - 카테고리 id를 반환")
    @PutMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<ReturnIdDTO> updateCategory(@PathVariable Long ledgerId, @PathVariable Long categoryId, @RequestBody CategoryReqDto categoryReqDto){
        ReturnIdDTO updatedId = categoryService.update(categoryId, categoryReqDto);
        return ResponseEntity.ok(updatedId);
    }

    // 카테고리 삭제
    @Operation(summary = "카테고리 삭제", description = "가계부 내 카테고리를 삭제한다.")
    @DeleteMapping("/{ledgerId}/category/{categoryId}")
    public ResponseEntity<ReturnIdDTO> deleteCategory(@PathVariable Long categoryId){
        categoryService.delete(categoryId);
        return ResponseEntity.ok(new ReturnIdDTO(categoryId));
    }


}