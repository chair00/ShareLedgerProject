package com.example.demo.service;

import com.example.demo.dto.JoinDTO;
import com.example.demo.dto.ResponseAction;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.JoinRepository;
import com.example.demo.repository.LedgerMemberRepository;
import com.example.demo.repository.LedgerRepository;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final LedgerRepository ledgerRepository;
    private final MemberRepository memberRepository;
    private final JoinRepository joinRepository;
    private final LedgerMemberService ledgerMemberService;

    // 생성
    @Transactional
    public ReturnIdDTO createJoin(Long ledgerId, Long memberId) {

        // 가계부 최대 생성 개수 확인
        ledgerMemberService.validateLedgerCount(memberId);

        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new IllegalArgumentException("가계부를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));

        ledgerMemberService.checkMemberNotInLedger(ledger, member);

        if (joinRepository.existsByLedgerAndMemberAndStatus(ledger, member, RequestStatus.PENDING)) {
            throw new IllegalStateException("이미 가입 요청을 보냈습니다.");
        }

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setLedger(ledger);
        joinRequest.setMember(member);
        joinRequest.setStatus(RequestStatus.PENDING);

        JoinRequest saved = joinRepository.save(joinRequest);
        return new ReturnIdDTO(saved);
    }

    // 가입 요청 목록 조회 (가계부 관리자가 확인)
    public List<JoinDTO.RequestData> getJoinData(Long ledgerId, Long ownerId) {
        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new IllegalArgumentException("가계부를 찾을 수 없습니다."));

        if (!ledger.getOwner().getId().equals(ownerId)) {
            throw new SecurityException("해당 가계부의 요청을 조회할 권한이 없습니다.");
        }

        List<JoinRequest> joinRequests = joinRepository.findAllByLedgerAndStatus(ledger, RequestStatus.PENDING);

        return joinRequests.stream()
                .map(joinRequest -> new JoinDTO.RequestData(
                        joinRequest.getId(),
                        joinRequest.getMember().getUsername(),
                        joinRequest.getMember().getName()
                )).collect(Collectors.toList());
    }

    // 응답
    @Transactional
    public void responseJoin(Long ledgerId, Long joinId, JoinDTO.Response res, Long ownerId) {
        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new IllegalArgumentException("가계부 id를 찾을 수 없습니다."));

//         권한 확인
        if (!ledger.getOwner().getId().equals(ownerId)) {
            throw new SecurityException("해당 가계부의 요청을 조회할 권한이 없습니다.");
        }
        // joinId 존재하는지 확인
        JoinRequest joinRequest = joinRepository.findById(joinId)
                .orElseThrow(() -> new IllegalArgumentException("요청을 찾을 수 없습니다."));

        if(joinRequest.getStatus() == RequestStatus.ACCEPTED || joinRequest.getStatus() == RequestStatus.DECLINED) {
            throw new IllegalStateException("이미 처리된 요청입니다.");
        }

        if(res.getAction() == ResponseAction.YES) {

            // 가계부 최대 생성 개수 확인
            // if 회원이 가입된 가계부 수가 9개인데, 2개의 가계부에 가입 요청을 보내고 모두 수락이 온다면, 총 가계부 수가 11개가 됨.
            // 이를 방지하기 위해 저장하기 전 한 번더 validate 검사
            ledgerMemberService.validateLedgerCount(joinRequest.getMember().getId());

            joinRequest.setStatus(RequestStatus.ACCEPTED);

            ledgerMemberService.saveMemberInLedger(
                    joinRequest.getLedger(),
                    joinRequest.getMember(),
                    LedgerRole.READ_ONLY);

        } else if(res.getAction() == ResponseAction.NO) {

            joinRequest.setStatus(RequestStatus.DECLINED);
        }

        joinRepository.save(joinRequest);
    }

    public boolean getResponseAction(Long joinId){
        JoinRequest join = joinRepository.findById(joinId)
                .orElseThrow(() -> new IllegalArgumentException("가입 요청을 찾을 수 없습니다."));

        if(join.getStatus() == RequestStatus.ACCEPTED) {
            return true;
        } else if (join.getStatus() == RequestStatus.DECLINED) {
            return false;
        } else {
            throw new IllegalStateException("해당 초대에 대한 응답이 처리되지 않았습니다.");
        }
    }

    public String getRequestMemberUsername(Long joinId) {
        JoinRequest join = joinRepository.findById(joinId)
                .orElseThrow(() -> new IllegalArgumentException("가입 요청을 찾을 수 없습니다."));

        return join.getMember().getUsername();
    }
}
