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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;
    private final LedgerMemberService ledgerMemberService;
    // private final LedgerMemberRepository ledgerMemberRepository;
    private final LedgerRepository ledgerRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReturnIdDTO createInvite(Long ledgerId, InviteDTO.Request req, Long ownerId) {

        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다."));
        Member member = memberRepository.findByUsername(req.getMemberUsername())
                .orElseThrow(() -> new IllegalArgumentException("멤버 username이 존재하지 않습니다."));

        //         권한 확인
        if (!ledger.getOwner().getId().equals(ownerId)) {
            throw new SecurityException("해당 가계부의 요청을 생성할 권한이 없습니다.");
        }

        ledgerMemberService.checkMemberNotInLedger(ledger, member);

        if (inviteRepository.existsByLedgerAndMemberAndStatus(ledger, member, RequestStatus.PENDING)) {
            throw new IllegalStateException("이미 가입 요청을 보냈습니다.");
        }

        Invite invite = Invite.builder()
                .ledger(ledger)
                .member(member)
                .status(RequestStatus.PENDING)
                .build();

        Invite saved = inviteRepository.save(invite);
        return new ReturnIdDTO(saved);
    }

    public List<InviteDTO.RequestData> getInviteData(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버 id가 없습니다."));
        List<Invite> invites = inviteRepository.findByMemberAndStatus(member, RequestStatus.PENDING);

        return invites.stream()
                .map(invite -> new InviteDTO.RequestData(
                        invite.getId(),
                        invite.getLedger().getName()
                )).collect(Collectors.toList());
    }

    @Transactional
    public void responseInvite(Long inviteId, InviteDTO.Response res) {
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("초대를 찾을 수 없습니다."));

        if(invite.getStatus() == RequestStatus.ACCEPTED || invite.getStatus() == RequestStatus.DECLINED) {
            throw new IllegalStateException("이미 처리된 요청입니다.");
        }

        if(res.getAction() == ResponseAction.YES) {

            ledgerMemberService.validateLedgerCount(invite.getMember().getId());

            invite.setStatus(RequestStatus.ACCEPTED);

            // LedgerMember에 저장 -> 기본 권한은 READ_ONLY 로 설정
            ledgerMemberService.saveMemberInLedger(
                    invite.getLedger(),
                    invite.getMember(),
                    LedgerRole.READ_ONLY);

        } else if (res.getAction() == ResponseAction.NO){

            invite.setStatus(RequestStatus.DECLINED);
        }

        inviteRepository.save(invite);
    }

    public String getLedgerOwnerUsername(Long inviteId){
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("초대를 찾을 수 없습니다."));

        return invite.getLedger().getOwner().getUsername();
    }

    // 이건 ledgerService로 옮겨야하나..
    public String getLedgerName(Long ledgerId){
        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new IllegalArgumentException("가계부를 찾을 수 없습니다."));

        return ledger.getName();
    }

    public String getMemberName(Long inviteId){
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("초대를 찾을 수 없습니다."));

        return invite.getMember().getName();
    }

    public boolean getResponseAction(Long inviteId){
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("초대를 찾을 수 없습니다."));

        if(invite.getStatus() == RequestStatus.ACCEPTED) {
            return true;
        } else if (invite.getStatus() == RequestStatus.DECLINED) {
            return false;
        } else {
            throw new IllegalStateException("해당 초대에 대한 응답이 처리되지 않았습니다.");
        }
    }
}
