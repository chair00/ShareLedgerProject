package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class HistoryController {
    // 내역 생성
    @PostMapping("/ledger/{ledger_id}/history")
    public ResponseEntity<?> createHistory(){return ResponseEntity.ok().body("내역 생성");}

    // 내역 상세 조회
    @GetMapping("/ledger/{ledger_id}/history/{history_id}")
    public ResponseEntity<?> showHistory(){return ResponseEntity.ok().body("내역 상세 조회");}

    // 내역 수정
    @PutMapping("/ledger/{ledger_id}/history/{history_id}")
    public ResponseEntity<?> updateHistory(){return ResponseEntity.ok().body("내역 수정");}

    // 내역 삭제
    @DeleteMapping("/ledger/{ledger_id}/history/{history_id}")
    public ResponseEntity<?> deleteHistory(){return ResponseEntity.ok().body("내역 삭제");}

    // 내역 목록 조회
    @GetMapping("/ledger/{ledger_id}/history")
    public ResponseEntity<?> showHistoryList(){return ResponseEntity.ok().body("내역 목록 조회");}

    // 총 금액 조회
    @GetMapping("/ledger/{ledger_id}/price")
    public ResponseEntity<?> showTotalPrice(){return ResponseEntity.ok().body("총 금액 조회");}

    // 총 금액 목록 조회
    @GetMapping("/ledger/{ledger_id}/prices")
    public ResponseEntity<?> showTotalPriceList(){return ResponseEntity.ok().body("총 금액 목록 조회");}
}
