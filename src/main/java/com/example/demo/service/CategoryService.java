package com.example.demo.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Ledger;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public CategoryDto find(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다."));
        return CategoryDto.builder().categoryName(category.getCategoryName()).kind(category.getKind()).build();
    }
//    @Transactional
//    public Long update(Long categoryId ,CategoryDto categoryDto) {
//
//    }
//
//    public void delete() {
//
//    }
}
