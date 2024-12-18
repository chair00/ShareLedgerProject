package com.example.demo.repository;

import com.example.demo.entity.Invite;
import com.example.demo.entity.Ledger;
import com.example.demo.entity.Member;
import com.example.demo.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    boolean existsByLedgerAndMemberAndStatus(Ledger ledger, Member member, RequestStatus status);

    List<Invite> findByMemberAndStatus(Member member, RequestStatus status);
}
