package com.example.demo.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Ledger;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final LedgerRepository ledgerRepository;



    public Long save(Long ledgerId, CategoryDto categoryDto) {
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다"));
        Category category = categoryDto.toEntity();

        category.setLedger(ledger);

        return categoryRepository.save(category).getId();
    }

    // 서브 카테고리 추가
    public Long save(Long ledgerId, Long categoryId, CategoryDto categoryDto) {
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다"));
        Category category = categoryDto.toEntity();

        Category parentCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다"));
        if (parentCategory.getParent() != null){
            throw new IllegalArgumentException("카테고리는 2계층을 넘을 수 없습니다.");
        }

        category.setLedger(ledger);
        category.setParent(parentCategory);

        return categoryRepository.save(category).getId();
    }

    public CategoryDto find(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다."));

        // 부모 카테고리가 없는 경우를 처리
        String parentName = (category.getParent() != null) ? category.getParent().getName() : null;

        return CategoryDto.builder().name(category.getName()).type(category.getType()).parentName(parentName).build();
    }

//    // 목록 가져오기
//    public List<CategoryDto> findAll(Long ledgerId) {
//        return categoryRepository.findAll().stream().map(CategoryDto::new).toList();
//    }

    @Transactional
    public Long update(Long categoryId, CategoryDto categoryDto) {
        Category category = find(categoryId).toEntity();

        category.setName(categoryDto.getName());

        return category.getId();
    }

    public void delete(Long categoryId) {

        Category category = find(categoryId).toEntity();

        categoryRepository.deleteById(category.getId());
    }

}
