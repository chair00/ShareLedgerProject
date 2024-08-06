package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Category;
import com.example.demo.entity.Ledger;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final LedgerRepository ledgerRepository;


    // 카테고리 생성
//    public Long save(Long ledgerId, CategoryReqDto categoryReqDto) {
//        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다"));
//        Category category = categoryReqDto.toEntity();
//
//        category.setLedger(ledger);
//
//        return categoryRepository.save(category).getId();
//    }

    public CategoryDTO.Response save(Long ledgerId, CategoryReqDto categoryReqDto) {
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다"));
        Category category = categoryReqDto.toEntity();

        category.setLedger(ledger);

        return new CategoryDTO.Response(categoryRepository.save(category));
    }

    // 서브 카테고리 생성
    public Long save(Long ledgerId, Long parentId, SubCategoryReqDto categoryReqDto) {
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다"));
        Category category = categoryReqDto.toEntity();

        Category parentCategory = categoryRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다"));
        if (parentCategory.getParent() != null){
            throw new IllegalArgumentException("카테고리는 2계층을 넘을 수 없습니다.");
        }

        category.setLedger(ledger);
        category.setParent(parentCategory);
        category.setType(parentCategory.getType());

        return categoryRepository.save(category).getId();
    }

    // 상세 조회
    public CategoryResDto find(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다."));

        // 부모 카테고리가 없는 경우를 처리
        Long parentId = (category.getParent() != null) ? category.getParent().getId() : null;
        List<CategoryResDto> children = categoryRepository.findByParentId(categoryId).stream().map(CategoryResDto::new).collect(Collectors.toList());

        return CategoryResDto.builder().name(category.getName()).type(category.getType()).parentCategoryId(parentId).children(children).build();
    }

    // 카테고리 목록 가져오기 (서브 카테고리 제외)
    public List<CategoryListResDto> findAll(Long ledgerId) {
//        return categoryRepository.findByLedgerIdAndParentIsNull(ledgerId).stream().map(CategoryResDto::new).collect(Collectors.toList());
        return categoryRepository.findByLedgerIdAndParentIsNull(ledgerId).stream().map(CategoryListResDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long categoryId, CategoryReqDto categoryReqDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다."));

        category.setName(categoryReqDto.getName());
        category.setType(categoryReqDto.getType());

        return category.getId();
    }

    public void delete(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다."));

        categoryRepository.deleteById(category.getId());
    }

}
