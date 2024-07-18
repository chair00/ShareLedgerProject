package com.example.demo.dto;

import com.example.demo.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
@AllArgsConstructor
public class CategoryResDto {

    private String name;
    private String type;
    private Long parentCategoryId;

    private List<CategoryResDto> children;

    public CategoryResDto(Category entity) {
        this.name = entity.getName();
        this.type = entity.getType();
        this.parentCategoryId = entity.getParent().getId();

    }
}
