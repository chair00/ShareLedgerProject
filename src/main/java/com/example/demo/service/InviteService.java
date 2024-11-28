package com.example.demo.service;

import com.example.demo.dto.InviteDTO;
import com.example.demo.dto.ResponseAction;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.InviteRepository;
import com.example.demo.repository.LedgerMemberRepository;
import com.example.demo.repository.LedgerRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;
    private final LedgerMemberRepository ledgerMemberRepository;
    private final LedgerRepository ledgerRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReturnIdDTO createInvite(Long ledgerId, InviteDTO.Request req) {
        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다."));
        Member member = memberRepository.findById(req.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("멤버 id가 존재하지 않습니다."));

        Invite invite = Invite.builder()
                .ledger(ledger)
                .member(member)
                .status(InviteStatus.PENDING)
                .build();

        Invite saved = inviteRepository.save(invite);
        return new ReturnIdDTO(saved);
    }

    @Transactional
    public void responseInvite(Long inviteId, InviteDTO.Response res) {
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("초대를 찾을 수 없습니다."));

        if(res.getAction() == ResponseAction.YES) {

            invite.setStatus(InviteStatus.ACCEPTED);

            // LedgerMember에 저장 -> 초대받는 경우 권한은 read_write 로 설정
            LedgerMember ledgerMember = LedgerMember.builder()
                    .ledger(invite.getLedger())
                    .member(invite.getMember())
                    .role(LedgerRole.READ_WRITE)
                    .build();

            ledgerMemberRepository.save(ledgerMember);
        } else if (res.getAction() == ResponseAction.NO){

            invite.setStatus(InviteStatus.DECLINED);
        }

        inviteRepository.save(invite);
    }
}
