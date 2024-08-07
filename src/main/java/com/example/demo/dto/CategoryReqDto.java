package com.example.demo.dto;

import com.example.demo.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor

@Schema(description = "카테고리 추가/수정 요청 DTO")
public class CategoryReqDto {

    @Schema(description = "카테고리 이름")
    @NotBlank(message = "카테고리 이름을 입력해야합니다.")
    private String name;

    @Schema(description = "수입/지출")
    @NotBlank(message = "카테고리 타입을 선택해야합니다.")
    private String type;

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .type(type)
                .build();
    }
}
