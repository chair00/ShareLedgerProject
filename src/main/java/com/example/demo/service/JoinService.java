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
    private final LedgerMemberRepository ledgerMemberRepository;

    // 생성
    @Transactional
    public ReturnIdDTO createJoin(Long ledgerId, Long memberId) {

        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new IllegalArgumentException("가계부를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));

        if (ledgerMemberRepository.existsByLedgerAndMember(ledger, member)) {
            throw new IllegalStateException("이미 해당 가계부에 가입된 회원입니다.");
        }

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

        System.out.println(res.getAction());

        if(res.getAction() == ResponseAction.YES) {

            joinRequest.setStatus(RequestStatus.ACCEPTED);

            LedgerMember ledgerMember = LedgerMember.builder()
                    .ledger(joinRequest.getLedger())
                    .member(joinRequest.getMember())
                    .role(LedgerRole.READ_ONLY)
                    .build();

            ledgerMemberRepository.save(ledgerMember);
        } else if(res.getAction() == ResponseAction.NO) {

            joinRequest.setStatus(RequestStatus.DECLINED);
        }

        joinRepository.save(joinRequest);
    }
}
