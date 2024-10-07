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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    ResponseDTO res = new ResponseDTO("성공");
    // 내역 생성
    @Operation(summary = "내역 생성", description = "가계부에 내역을 생성한다.")
    @PostMapping("/ledger/{ledgerId}/history")
    public ResponseEntity<ReturnIdDTO> createHistory(@PathVariable Long ledgerId, @Valid @RequestBody HistoryDTO.Create historyReqDto){

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
//    @Operation(summary = "내역 수정", description = "생성한 내역을 수정한다.")
//    @PutMapping("/ledger/{ledgerId}/history/{historyId}")
//    public ResponseEntity<ReturnIdDTO> updateHistory(@PathVariable Long historyId, @RequestBody HistoryDTO.Update historyReqDto){
//
//        ReturnIdDTO updatedId = historyService.update(historyId, historyReqDto);
//        return ResponseEntity.ok(updatedId);
//    }

    // 내역 삭제
    @Operation(summary = "내역 삭제", description = "생성한 내역을 삭제한다.")
    @DeleteMapping("/ledger/{ledger_id}/history/{history_id}")
    public ResponseEntity<?> deleteHistory(){
        return ResponseEntity.ok(res);
    }

    // 내역 목록 조회
    @Operation(summary = "내역 목록 조회", description = "일간/월간 내역 목록을 조회한다.")
    @GetMapping("/ledger/{ledger_id}/history")
    public ResponseEntity<?> showHistoryList(){
        return ResponseEntity.ok(res);
    }

    // 총 금액 조회
    @Operation(summary = "총 금액 조회", description = "일간/월간/연간/사용자 지정 조건에 따라 총 금액을 조회한다.")
    @GetMapping("/ledger/{ledger_id}/price")
    public ResponseEntity<?> showTotalPrice(){
        return ResponseEntity.ok(res);
    }

    // 총 금액 목록 조회
    @Operation(summary = "총 금액 목록 조회", description = "일간/월간/카테고리 별 등 조건에 따라 목록을 조회한다.")
    @GetMapping("/ledger/{ledger_id}/prices")
    public ResponseEntity<?> showTotalPriceList(){
        return ResponseEntity.ok(res);
    }
}
