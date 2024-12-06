package com.example.demo.repository;

import com.example.demo.entity.Ledger;
import com.example.demo.entity.LedgerMember;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerMemberRepository extends JpaRepository<LedgerMember, Long> {
    List<LedgerMember> findByLedgerId(Long ledgerId);
    List<LedgerMember> findByMemberId(Long memberId);

    boolean existsByLedgerAndMember(Ledger ledger, Member member);
}
