package com.example.demo.dto;

import com.example.demo.entity.Category;
import com.example.demo.entity.History;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryDTO {

    @Getter
    @Setter
    @Schema(description = "내역 생성/수정 요청")
    public static class Request {
        @Schema(description = "내역명")
        @NotBlank(message = "내역명을 입력해야합니다.")
        private String name;

        @Schema(description = "가격")
        @NotBlank(message = "가격을 입력해야합니다.")
        private Long price;

        @Schema(description = "날짜")
        @NotBlank(message = "날짜를 입력해야합니다.")
        private LocalDate date;

        @Schema(description = "카테고리")
        @NotBlank(message = "카테고리를 선택해야합니다.")
        private Long categoryId;

        public History toEntity() {
            return History.builder()
                    .name(name)
                    .price(price)
                    .date(date)
                    .build();
        }
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "내역 데이터 응답")
    public static class Response {

        @Schema(description = "내역 id")
        private Long id;

        @Schema(description = "내역명")
        private String name;

        @Schema(description = "가격")
        private Long price;

        @Schema(description = "날짜")
        private LocalDate date;

        @Schema(description = "카테고리")
        private String category;

        @Schema(description = "가계부 id")
        private Long ledgerId;


        public Response(History entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.price = entity.getPrice();
            this.date = entity.getDate();
            this.category = entity.getCategory().getName();
            this.ledgerId = entity.getLedger().getId();
        }

        @Schema(description = "내역 목록 응답")
        public static List<HistoryDTO.Response> ResponseList(List<History> historyList) {
            List<HistoryDTO.Response> responseList = historyList.stream()
                    .map(o->new HistoryDTO.Response(o))
                    .collect(Collectors.toList());
            return responseList;
        }


    }

    @Getter
    @Setter
    @Schema(description = "총금액 응답")
    public static class ResponseTotalPrice {
        @Schema(description = "총 금액")
        private Long price;

        public ResponseTotalPrice(Long price) {
            this.price = price;
        }
    }
}
