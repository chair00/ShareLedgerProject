package com.example.demo.dto;

import com.example.demo.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CategoryListResDto {
    private String name;
    private String type;

    public CategoryListResDto(Category entity) {
        this.name = entity.getName();
        this.type = entity.getType();
    }
}
