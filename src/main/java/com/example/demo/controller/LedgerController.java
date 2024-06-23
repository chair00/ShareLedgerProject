package com.example.demo.controller;

import com.example.demo.dto.LedgerDto;
import com.example.demo.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    // 가계부 생성
    @PostMapping("")
    public ResponseEntity<Long> createLedger(@RequestBody LedgerDto ledgerDto){
        Long createdLedgerId = ledgerService.createLedger(ledgerDto);
        return ResponseEntity.ok(createdLedgerId);
    }

    // 가계부 정보 조회
    @GetMapping("/{ledgerId}")
    public ResponseEntity<LedgerDto> getLedger(@PathVariable Long ledgerId){
        LedgerDto ledger = ledgerService.getLedger(ledgerId);
        return ResponseEntity.ok(ledger);
    }

    // 가계부 정보 수정
    @PutMapping("/{ledgerId}")
    public ResponseEntity<LedgerDto> updateLedger(@PathVariable Long ledgerId, @RequestBody LedgerDto ledgerDto){
        LedgerDto updatedLedger = ledgerService.updateLedger(ledgerId, ledgerDto);
        return ResponseEntity.ok(updatedLedger);
    }

    // 가계부 삭제
    @DeleteMapping("/{ledgerId}")
    public ResponseEntity<LedgerDto> deleteLedger(@PathVariable Long ledgerId){
        ledgerService.deleteLedger(ledgerId);
        return ResponseEntity.status(HttpStatus.OK).build(); // body없을 때 build() 사용
    }
}