package com.example.demo.dto;

import com.example.demo.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor
public class CategoryDto {
    @NotBlank(message = "카테고리 이름을 입력해야합니다.")
    private String name;

    @NotBlank(message = "카테고리 타입을 선택해야합니다.")
    private String type;

    private String parentName;

    private Map<String, CategoryDto> children;

//    public CategoryDto(Category entity) {
//        this.name = entity.getName();
//        this.type = entity.getType();
//
//        this.children = entity.getChild() == null ? null :
//                entity.getChild().stream().collect(
//                        Collectors.toMap(Category::getName, CategoryDto::new));
//    }

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .type(type)
                .build();
    }
}
