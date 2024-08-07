package com.example.demo.dto;

import com.example.demo.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryDTO {

    @Getter
    @Setter
    @Schema(description = "카테고리 생성 요청")
    public static class Create {
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
    @Getter
    @Setter
    @Schema(description = "하위 카테고리 생성 요청")
    public static class SubCreate{
        @Schema(description = "카테고리 이름")
        @NotBlank(message = "카테고리 이름을 입력해야합니다.")
        private String name;

        public Category toEntity() {
            return Category.builder()
                    .name(name)
                    .build();
        }

    }

    @Getter
    @Setter
    @Schema(description = "카테고리 수정 요청")
    public static class Update {
        @Schema(description = "카테고리 이름")
        private String name;

        @Schema(description = "수입/지출")
        private String type;

        public Category toEntity() {
            return Category.builder()
                    .name(name)
                    .type(type)
                    .build();
        }

    }

    @Getter
    @Setter
    @Schema(description = "카테고리 데이터 응답")
    public static class Response {

        @Schema(description = "카테고리 이름")
        private String name;

        @Schema(description = "수입/지출")
        private String type;

        @Schema(description = "상위 카테고리 id")
        private Long parentCategoryId;

        @Schema(description = "하위 카테고리 list")
        private List<CategoryDTO.Response> children;

        public Response(Category entity) {
            this.name = entity.getName();
            this.type = entity.getType();

            if (entity.getParent() != null){
                this.parentCategoryId = entity.getParent().getId();
            }
        }

        public Response(Category entity, List<CategoryDTO.Response> children) {
            this.name = entity.getName();
            this.type = entity.getType();

            if (entity.getParent() != null){
                this.parentCategoryId = entity.getParent().getId();
            }
            this.children = children;
        }

        @Schema(description = "상위 카테고리 목록 응답")
        public static List<Response> ResponseList(List<Category> categoryList) {
            List<Response> responseList = categoryList.stream()
                    .map(o->new Response(o))
                    .collect(Collectors.toList());
            return responseList;
        }
    }

}
