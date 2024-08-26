package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class HistoryController {
    ResponseDTO res = new ResponseDTO("성공");
    // 내역 생성
    @Operation(summary = "내역 생성", description = "가계부에 내역을 생성한다.")
    @PostMapping("/ledger/{ledger_id}/history")
    public ResponseEntity<?> createHistory(){
        return ResponseEntity.ok(res);
    }

    // 내역 상세 조회
    @Operation(summary = "내역 상세 조회", description = "생성한 내역을 상세 조회한다.")
    @GetMapping("/ledger/{ledger_id}/history/{history_id}")
    public ResponseEntity<?> showHistory(){
        return ResponseEntity.ok(res);
    }

    // 내역 수정
    @Operation(summary = "내역 수정", description = "생성한 내역을 수정한다.")
    @PutMapping("/ledger/{ledger_id}/history/{history_id}")
    public ResponseEntity<?> updateHistory(){
        return ResponseEntity.ok(res);
    }

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
