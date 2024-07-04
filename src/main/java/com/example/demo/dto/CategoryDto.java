package com.example.demo.dto;

import com.example.demo.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor
public class CategoryDto {
    private Long categoryId;

    private String name;

    private String type;

    private String parentCategoryName;

    private Integer level;

    private Map<String, CategoryDto> children;

    public CategoryDto(Category entity) {
        this.categoryId = entity.getId();
        this.name = entity.getName();
        this.type = entity.getType();
        if(entity.getParent() == null) {
            this.parentCategoryName = "대분류";
        } else {
            this.parentCategoryName = entity.getParent().getName();
        }

        this.children = entity.getChild() == null ? null :
                entity.getChild().stream().collect(
                        Collectors.toMap(Category::getName, CategoryDto::new));
    }

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .type(type)
                .level(level)
                .build();
    }
}
