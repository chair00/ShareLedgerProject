package com.example.demo.controller;

import com.example.demo.dto.LedgerDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.dto.signUp.CustomUserDetails;
import com.example.demo.response.ApiResult;
import com.example.demo.service.LedgerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ledger")
@RequiredArgsConstructor

@Tag(name = "가계부", description = "가계부 API")
public class LedgerController {

    private final LedgerService ledgerService;

    // 가계부 생성
    @Operation(summary = "가계부 생성", description = "새로운 가계부를 생성한다. 생성한 사용자는 그 가계부의 관리자가 된다.")
    @PostMapping("")
    public ResponseEntity<ReturnIdDTO> createLedger(@RequestBody LedgerDTO.RequestDTO ledgerDto, @AuthenticationPrincipal CustomUserDetails userDetails){
        ReturnIdDTO createdId = ledgerService.createLedger(ledgerDto, userDetails.getId());
        return ResponseEntity.created(URI.create("/ledger/" + createdId)).body(createdId);
    }

    // 가계부 정보 조회
    @Operation(summary = "가계부 정보 조회", description = "가계부 정보를 조회한다.")
    @GetMapping("/{ledgerId}")
    public ResponseEntity<LedgerDTO.ResponseDTO> getLedger(@PathVariable Long ledgerId){
        LedgerDTO.ResponseDTO ledger = ledgerService.getLedger(ledgerId);
        return ResponseEntity.ok(ledger);
    }

    // 가계부 정보 수정
    @Operation(summary = "가계부 정보 수정", description = "가계부 정보를 수정한다.")
    @PutMapping("/{ledgerId}")
    public ResponseEntity<ReturnIdDTO> updateLedger(@PathVariable Long ledgerId, @RequestBody LedgerDTO.RequestDTO ledgerDto){
        ReturnIdDTO updatedId = ledgerService.updateLedger(ledgerId, ledgerDto);
        return ResponseEntity.ok(updatedId);
    }

    // 가계부 삭제
    @Operation(summary = "가계부 삭제", description = "관리자가 가계부를 삭제한다.")
    @DeleteMapping("/{ledgerId}")
    public ResponseEntity<ReturnIdDTO> deleteLedger(@PathVariable Long ledgerId, @AuthenticationPrincipal CustomUserDetails userDetails){
        ledgerService.deleteLedger(ledgerId, userDetails.getId());
        return ResponseEntity.ok(new ReturnIdDTO(ledgerId)); // body없을 때 build() 사용
    }

    // 가계부 검색
    @Operation(summary = "가계부 검색", description = "가계부 이름으로 가계부를 검색한다.")
    @GetMapping("/search")
    public ResponseEntity<List<LedgerDTO.ResponseDTO>> searchLedger(@RequestParam String ledgerName){
        List<LedgerDTO.ResponseDTO> ledgers = ledgerService.findLedger(ledgerName);
        return ResponseEntity.ok(ledgers);
    }
}