package com.example.demo.dto;

import com.example.demo.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryDto {
    private String categoryName;
    private String kind;
    private Long ledgerId;

    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .kind(kind)
                .build();
    }
}
