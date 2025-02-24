package com.example.demo.service;

import com.example.demo.dto.LedgerDTO;
import com.example.demo.repository.LedgerMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.entity.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LedgerMemberService {

    private static final int MAX_LEDGER_COUNT = 10;

    private final LedgerMemberRepository ledgerMemberRepository;

    public List<LedgerMember> findByMemberId(Long memberId) {
        return ledgerMemberRepository.findByMemberId(memberId);
    }

    public List<LedgerDTO.ResponseDTO> findLedgerDTOByMemberId(Long memberId) {
        return findByMemberId(memberId)
                .stream()
                .map(ledgerMember -> {
                    Ledger ledger = ledgerMember.getLedger();
                    return new LedgerDTO.ResponseDTO(
                            ledger.getId(),
                            ledger.getName(),
                            ledger.getOwner().getUsername()
                    );
                })
                .collect(Collectors.toList());
    }

    public void validateLedgerCount(Long memberId) {
        List<LedgerMember> ledgers = ledgerMemberRepository.findByMemberId(memberId);
        if (ledgers.size() >= MAX_LEDGER_COUNT) {
            throw new IllegalStateException("회원이 가입할 수 있는 가계부는 최대 " + MAX_LEDGER_COUNT + "개 입니다.");
        }
    }

    public void checkMemberNotInLedger(Ledger ledger, Member member) {
        if (ledgerMemberRepository.existsByLedgerAndMember(ledger, member)) {
            throw new IllegalStateException("이미 해당 가계부에 가입된 회원입니다.");
        }
    }

    public LedgerMember saveMemberInLedger(Ledger ledger, Member member, LedgerRole role) {

        LedgerMember ledgerMember = LedgerMember.builder()
                .ledger(ledger)
                .member(member)
                .role(role)
                .build();

        return ledgerMemberRepository.save(ledgerMember);
    }

    @Transactional
    public void deleteMemberFromLedger(LedgerMember ledgerMember) {

        ledgerMemberRepository.delete(ledgerMember);
    }

    public List<LedgerMember> findByLedgerId(Long ledgerId) {
        return ledgerMemberRepository.findByLedgerId(ledgerId);
    }

    @Transactional
    public void changeRole(Ledger ledger, Member member, LedgerRole role) {
        LedgerMember ledgerMember = ledgerMemberRepository.findByLedgerAndMember(ledger, member)
                .orElseThrow(() -> new IllegalArgumentException("해당 가계부에 해당 멤버가 존재하지 않습니다."));

        ledgerMember.setRole(role);
        ledgerMemberRepository.save(ledgerMember);
    }

    public LedgerMember findByLedgerIdAndMemberId(Long ledgerId, Long memberId) {

        LedgerMember ledgerMember = ledgerMemberRepository.findByLedgerIdAndMemberId(ledgerId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가계부에 멤버가 존재하지 않습니다."));

        return ledgerMember;
    }
}
