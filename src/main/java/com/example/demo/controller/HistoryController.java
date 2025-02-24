package com.example.demo.controller;

import com.example.demo.dto.HistoryDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.dto.signUp.CustomUserDetails;
import com.example.demo.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor

@Tag(name = "내역", description = "내역 API")
public class HistoryController {

    private final HistoryService historyService;

    ResponseDTO res = new ResponseDTO("성공");
    // 내역 생성
    @Operation(summary = "내역 생성", description = "가계부에 내역을 생성한다." +
            "date 형식은 yyyyMMddHHmm (문자열)")
    @PostMapping("/ledger/{ledgerId}/history")
    public ResponseEntity<ReturnIdDTO> createHistory(@PathVariable Long ledgerId,
                                                     @Valid @RequestBody HistoryDTO.HistoryRequest historyReqDto,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails){

        Long memberId = userDetails.getId();

        ReturnIdDTO createdId = historyService.save(ledgerId, historyReqDto, memberId);
        return ResponseEntity.ok(createdId);
    }

    // 내역 상세 조회
    @Operation(summary = "내역 상세 조회", description = "생성한 내역을 상세 조회한다.")
    @GetMapping("/ledger/{ledgerId}/history/{historyId}")
    public ResponseEntity<HistoryDTO.HistoryResponse> getHistory(@PathVariable Long historyId){

        HistoryDTO.HistoryResponse history = historyService.find(historyId);
        return ResponseEntity.ok(history);
    }

    // 내역 수정
    @Operation(summary = "내역 수정", description = "생성한 내역을 수정한다.")
    @PutMapping("/ledger/{ledgerId}/history/{historyId}")
    public ResponseEntity<ReturnIdDTO> updateHistory(@PathVariable Long historyId, @RequestBody HistoryDTO.HistoryRequest historyReqDto){

        ReturnIdDTO updatedId = historyService.update(historyId, historyReqDto);
        return ResponseEntity.ok(updatedId);
    }

    // 내역 삭제
    @Operation(summary = "내역 삭제", description = "생성한 내역을 삭제한다.")
    @DeleteMapping("/ledger/{ledgerId}/history/{historyId}")
    public ResponseEntity<ReturnIdDTO> deleteHistory(@PathVariable Long historyId){

        historyService.delete(historyId);
        return ResponseEntity.ok(new ReturnIdDTO(historyId));
    }

//    // 내역 목록 조회(월간/일간)
//    @Operation(summary = "일간/월간 내역 목록 조회", description = "일간/월간 내역 목록을 조회한다." +
//            "ex. \"/ledger/1/history?startDate=20240701&endDate=20240729\" 형식으로 요청")
//    @GetMapping("/ledger/{ledgerId}/history")
//    public ResponseEntity<List<HistoryDTO.Response>> showHistoryList(@PathVariable Long ledgerId,
//                                                                     @RequestParam("startDate") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
//                                                                     @RequestParam("endDate") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate){
//        List<HistoryDTO.Response> historyList = historyService.findByDate(ledgerId, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
//        return ResponseEntity.ok(historyList);
//    }

    // 총 금액 조회
    @Operation(summary = "총 금액 조회", description = "일간/월간/연간/사용자 지정 조건에 따라 총 금액(수입, 지출, 수입-지출)을 조회한다." +
            "ex. \"/ledger/1/history?startDate=20240701&endDate=20240729\" 형식으로 요청")
    @GetMapping("/ledger/{ledgerId}/price")
    public ResponseEntity<HistoryDTO.ResponseTotalPrice> showTotalPrice(@PathVariable Long ledgerId,
                                                                        @RequestParam("startDate") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
                                                                        @RequestParam("endDate") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate){
        return ResponseEntity.ok(historyService.getPriceSumForDateRange(ledgerId, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)));
    }

    // 카테고리별 검색 기능 (카테고리 id로 검색)
    @Operation(summary = "내역 목록 조회(시작날짜, 끝날짜, 카테고리 조건 설정 가능)", description = "일간/월간/연간/사용자 설정 기간/카테고리 지정 조건에 따라 내역 목록을 조회한다." +
            "ex. \"/ledger/1/search?categories=5&categories=6&startDate=20240706&endDate=20240731\" 형식으로 요청")
    @GetMapping("/ledger/{ledgerId}/search")
    public ResponseEntity<List<HistoryDTO.HistoryResponse>> searchByConditions (@PathVariable Long ledgerId,
                                                                                @RequestParam(required = false) List<Long> categories,
                                                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
                                                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate) {

        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        return ResponseEntity.ok(historyService.findByConditions(ledgerId, categories, startDateTime, endDateTime));
    }

    // 만들어야함 (통계 만들때)
//    // 총 금액 목록 조회
//    @Operation(summary = "총 금액 목록 조회", description = "일간/월간/카테고리 별 등 조건에 따라 목록을 조회한다.")
//    @GetMapping("/ledger/{ledgerId}/prices")
//    public ResponseEntity<List<HistoryDTO.Response>> showTotalPriceList(@PathVariable Long ledgerId, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
//
//        return ResponseEntity.ok(res);
//    }
}
