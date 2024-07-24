package com.example.demo.service;

import com.example.demo.dto.LedgerDto;
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
    public Long createLedger(LedgerDto ledgerDto) {

        return ledgerRepository.save(ledgerDto.toEntity()).getId();

    }

    // 가계부 정보 조회
    public LedgerDto getLedger(Long ledgerId) {

        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다."));

        return new LedgerDto(ledger);

    }

    // 가계부 정보 수정
    @Transactional
    public Long updateLedger(Long ledgerId, LedgerDto ledgerDto) {
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다."));
        ledger.setLedgerName(ledgerDto.getLedgerName());

        return ledger.getId();
    }

    // 가계부 삭제
    public void deleteLedger(Long ledgerId) {
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다."));
        ledgerRepository.delete(ledger);
    }

}