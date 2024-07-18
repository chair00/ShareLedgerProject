package com.example.demo.dto;

import com.example.demo.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
@AllArgsConstructor

@Schema(description = "카테고리 응답 DTO")
public class CategoryResDto {
    @Schema(description = "카테고리 이름")
    private String name;

    @Schema(description = "수입/지출")
    private String type;

    @Schema(description = "상위 카테고리 id")
    private Long parentCategoryId;

    @Schema(description = "하위 카테고리 list")
    private List<CategoryResDto> children;

    public CategoryResDto(Category entity) {
        this.name = entity.getName();
        this.type = entity.getType();
        if (entity.getParent() != null){
            this.parentCategoryId = entity.getParent().getId();
        }
    }
}
