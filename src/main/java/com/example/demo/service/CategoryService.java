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

        if (categoryDto.getParentCategoryName() == null){
            if (categoryRepository.existsByName(categoryDto.getParentCategoryName())){
                throw new IllegalArgumentException("이미 존재하는 이름입니다.");
            }
            category.setParent(null);
            category.setLevel(0);
        } else {
            Category parentCategory = categoryRepository.findByName(categoryDto.getParentCategoryName()).orElseThrow(() -> new IllegalArgumentException("상위 카테고리 이름이 존재하지 않습니다"));
            category.setParent(parentCategory);
            category.setLevel(1);
        }

        return categoryRepository.save(category).getId();
    }

    public CategoryDto find(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다."));
        return CategoryDto.builder().name(category.getName()).type(category.getType()).build();
    }

    // 목록 가져오기
    // 하위 카테고리 불러오기

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
