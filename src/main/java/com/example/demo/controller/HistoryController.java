package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.HistoryDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.response.ApiResult;
import com.example.demo.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    ResponseDTO res = new ResponseDTO("성공");
    // 내역 생성
    @Operation(summary = "내역 생성", description = "가계부에 내역을 생성한다.")
    @PostMapping("/ledger/{ledgerId}/history")
    public ResponseEntity<ReturnIdDTO> createHistory(@PathVariable Long ledgerId, @Valid @RequestBody HistoryDTO.Request historyReqDto){

        ReturnIdDTO createdId = historyService.save(ledgerId, historyReqDto);
        return ResponseEntity.ok(createdId);
    }

    // 내역 상세 조회
    @Operation(summary = "내역 상세 조회", description = "생성한 내역을 상세 조회한다.")
    @GetMapping("/ledger/{ledgerId}/history/{historyId}")
    public ResponseEntity<HistoryDTO.Response> getHistory(@PathVariable Long historyId){

        HistoryDTO.Response history = historyService.find(historyId);
        return ResponseEntity.ok(history);
    }

    // 내역 수정
    @Operation(summary = "내역 수정", description = "생성한 내역을 수정한다.")
    @PutMapping("/ledger/{ledgerId}/history/{historyId}")
    public ResponseEntity<ReturnIdDTO> updateHistory(@PathVariable Long historyId, @RequestBody HistoryDTO.Request historyReqDto){

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

    // 내역 목록 조회(월간/일간)
    @Operation(summary = "일간/월간 내역 목록 조회", description = "일간/월간 내역 목록을 조회한다.")
    @GetMapping("/ledger/{ledgerId}/history")
    public ResponseEntity<List<HistoryDTO.Response>> showHistoryList(@PathVariable Long ledgerId, @RequestParam(value = "year", required = true) String year,
                                                                     @RequestParam(value = "month", required = true) String month, @RequestParam(value = "day", required = false, defaultValue = "-1") String day){
        List<HistoryDTO.Response> historyList = historyService.findByDate1(ledgerId, year, month, day);
        return ResponseEntity.ok(historyList);
    }

//    // 내역 목록 조회(월간)
//    @Operation(summary = "월간 내역 목록 조회", description = "월간 내역 목록을 조회한다.")
//    @GetMapping("/ledger/{ledgerId}/history")
//    public ResponseEntity<List<HistoryDTO.Response>> showMonthHistoryList(@PathVariable Long ledgerId, int year, int month){
//        List<HistoryDTO.Response> historyList = historyService.findByMonth(ledgerId, year, month);
//        return ResponseEntity.ok(historyList);
//    }
//
//    // 내역 목록 조회(일간)
//    @Operation(summary = "일간 내역 목록 조회", description = "일간 내역 목록을 조회한다.")
//    @GetMapping("/ledger/{ledgerId}/history")
//    public ResponseEntity<List<HistoryDTO.Response>> showDateHistoryList(@PathVariable Long ledgerId, LocalDate date){
//        List<HistoryDTO.Response> historyList = historyService.findByDate(ledgerId, date);
//        return ResponseEntity.ok(historyList);
//    }

    // 총 금액 조회
    @Operation(summary = "총 금액 조회", description = "일간/월간/연간/사용자 지정 조건에 따라 총 금액을 조회한다.")
    @GetMapping("/ledger/{ledgerId}/price")
    public ResponseEntity<HistoryDTO.ResponseTotalPrice> showTotalPrice(@PathVariable Long ledgerId, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        return ResponseEntity.ok(historyService.getPriceSumForDateRange(ledgerId, startDate, endDate));
    }

    //만들어야함
//    // 총 금액 목록 조회
//    @Operation(summary = "총 금액 목록 조회", description = "일간/월간/카테고리 별 등 조건에 따라 목록을 조회한다.")
//    @GetMapping("/ledger/{ledgerId}/prices")
//    public ResponseEntity<List<HistoryDTO.Response>> showTotalPriceList(@PathVariable Long ledgerId, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
//
//        return ResponseEntity.ok(res);
//    }
}
