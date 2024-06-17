package com.example.demo.controller;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.LedgerDto;
import com.example.demo.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    // 가계부 생성
    @PostMapping("")
    public ResponseEntity<Long> createLedger(@RequestBody LedgerDto ledgerDto){
        Long createdLedgerId = ledgerService.createLedger(ledgerDto);
        return ResponseEntity.ok(createdLedgerId);
    }

    // 가계부 정보 조회
    @GetMapping("/{ledgerId}")
    public ResponseEntity<LedgerDto> getLedger(@PathVariable Long ledgerId){
        LedgerDto ledger = ledgerService.getLedger(ledgerId);
        return ResponseEntity.ok(ledger);
    }

    // 가계부 정보 수정
    @PutMapping("/{ledgerId}")
    public ResponseEntity<LedgerDto> updateLedger(@PathVariable Long ledgerId, @RequestBody LedgerDto ledgerDto){
        LedgerDto updatedledger = ledgerService.updateLedger(ledgerId, ledgerDto);
        return ResponseEntity.ok(updatedLedger);
    }

    // 가계부 삭제
    @DeleteMapping("/{ledgerId}")
    public ResponseEntity<LedgerDto> deleteLedger(@PathVariable Long ledgerId){
        ledgerService.deleteLedger(ledgerId);
        return ResponseEntity.status(HttpStatus.OK).build(); // body없을 때 build() 사용
    }
//
//    // 카테고리 추가
//    @PostMapping("/{ledgerId}/category")
//    public ResponseEntity<CategoryDto> createCategory(@PathVariable Long ledgerId, @RequestBody CategoryDto categoryDto){
//        CategoryDto createdCategory = ledgerService.createLedger(categoryDto);
//        return ResponseEntity.ok(createdCategory);
//    }
//
//    // 카테고리 상세 조회
//    @GetMapping("/{ledgerId}/category/{categoryId}")
//    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long ledgerId, @PathVariable Long categoryId){
//        CategoryDto category = ledgerService.getCategory(ledgerId, categoryId);
//        return ResponseEntity.ok(category);
//    }
//
//    // 카테고리 목록 조회
//    @GetMapping("/{ledgerId}/category")
//    public ResponseEntity<List<CategoryDto>> getCategoryList(@PathVariable Long ledgerId){
//        List<CategoryDto> categoryList = ledgerService.getCategoryList(ledgerId);
//        return ResponseEntity.ok(categoryList);
//    }
//
//    // 카테고리 수정
//    @PutMapping("/{ledgerId}/category/{categoryId}")
//    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long ledgerId, @PathVariable Long categoryId, @RequestBody CategoryDto categoryDto){
//        CategoryDto updatedCategory = ledgerService.updateCategory(ledgerId, categoryId, categoryDto);
//        return ResponseEntity.ok(updatedCategory);
//    }
//
//    // 카테고리 삭제
//    @DeleteMapping("/{ledgerId}/category/{categoryId}")
//    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long ledgerId, @PathVariable Long categoryId){
//        ledgerService.deleteCategory(ledgerId, categoryId);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
}