package com.example.demo.repository;

import com.example.demo.entity.JoinRequest;
import com.example.demo.entity.Ledger;
import com.example.demo.entity.Member;
import com.example.demo.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRepository extends JpaRepository<JoinRequest, Long> {
    boolean existsByLedgerAndMemberAndStatus(Ledger ledger, Member member, RequestStatus status);

    List<JoinRequest> findAllByLedgerAndStatus(Ledger ledger, RequestStatus status);
}
