package com.example.demo.service;

import com.example.demo.dto.LedgerDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.entity.Ledger;
import com.example.demo.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    // 가계부 생성
    public ReturnIdDTO createLedger(LedgerDTO ledgerDto) {

        return new ReturnIdDTO(ledgerRepository.save(ledgerDto.toEntity()));

    }

    // 가계부 정보 조회
    public LedgerDTO getLedger(Long ledgerId) {

        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다."));

        return new LedgerDTO(ledger);

    }

    // 가계부 정보 수정
    @Transactional
    public ReturnIdDTO updateLedger(Long ledgerId, LedgerDTO ledgerDto) {
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다."));
        ledger.setLedgerName(ledgerDto.getLedgerName());

        return new ReturnIdDTO(ledger);
    }

    // 가계부 삭제
    public void deleteLedger(Long ledgerId) {
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다."));
        ledgerRepository.delete(ledger);
    }

}