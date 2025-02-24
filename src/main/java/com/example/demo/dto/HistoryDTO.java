package com.example.demo.dto;

import com.example.demo.entity.History;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryDTO {

    @Getter
    @Setter
    @Schema(description = "내역 생성/수정 요청")
    public static class HistoryRequest {
        @Schema(description = "내역명")
        @NotBlank(message = "내역명을 입력해야합니다.")
        private String name;

        @Schema(description = "가격")
        @NotNull(message = "가격을 입력해야합니다.")
        private Long price;

        @Schema(description = "날짜")
        @NotNull(message = "날짜를 입력해야합니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmm", timezone = "Asia/Seoul")
        private LocalDateTime date;

        @Schema(description = "카테고리")
        @NotNull(message = "카테고리를 선택해야합니다.")
        private Long categoryId;

        @Schema(description = "메모")
        private String memo;

        public History toEntity() {
//            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
//            LocalDateTime strToLocalDateTime = LocalDateTime.parse(date, format);

            return History.builder()
                    .name(name)
                    .price(price)
                    .date(date)
                    .memo(memo)
                    .build();
        }
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "내역 데이터 응답")
    public static class HistoryResponse {

        @Schema(description = "내역 id")
        private Long id;

        @Schema(description = "내역명")
        private String name;

        @Schema(description = "가격")
        private Long price;

        @Schema(description = "날짜")
        private LocalDateTime date;

        @Schema(description = "카테고리")
        private String category;

        @Schema(description = "카테고리 타입(IN/OUT)")
        private String categoryType;

        @Schema(description = "부모 카테고리")
        private String parentCategory;

        @Schema(description = "메모")
        private String memo;

        @Schema(description = "가계부 id")
        private Long ledgerId;


        public HistoryResponse(History entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.price = entity.getPrice();
            this.date = entity.getDate();
            this.category = entity.getCategory().getName();
            this.categoryType = entity.getCategory().getType().toString();

            if (entity.getCategory().getParent() != null){
                this.parentCategory = entity.getCategory().getParent().getName();
            }

            this.memo = entity.getMemo();
            this.ledgerId = entity.getLedger().getId();
        }

        @Schema(description = "내역 목록 응답")
        public static List<HistoryResponse> ResponseList(List<History> historyList) {
            List<HistoryResponse> responseList = historyList.stream()
                    .map(o->new HistoryResponse(o))
                    .collect(Collectors.toList());
            return responseList;
        }


    }

    @Getter
    @Setter
    @Schema(description = "총금액 응답")
    public static class ResponseTotalPrice {

        @Schema(description = "수입 총 금액")
        private Long totalInPrice;

        @Schema(description = "지출 총 금액")
        private Long totalOutPrice;

        @Schema(description = "수입-지출 총 금액")
        private Long totalPrice;

        public ResponseTotalPrice(Long totalInPrice, Long totalOutPrice, Long totalPrice) {
            this.totalInPrice = totalInPrice;
            this.totalOutPrice = totalOutPrice;
            this.totalPrice = totalPrice;
        }
    }
}
