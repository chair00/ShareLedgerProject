package com.example.demo.controller;

import com.example.demo.dto.JoinDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.dto.signUp.CustomUserDetails;
import com.example.demo.service.JoinService;
import com.example.demo.service.LedgerService;
import com.example.demo.service.MemberService;
import com.example.demo.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor

@Tag(name = "가계부 가입 요청", description = "가계부 가입 요청 API")
public class JoinController {

    private final JoinService joinService;
    private final NotificationService notificationService;
    private final MemberService memberService;
    private final LedgerService ledgerService;

    // 가입 요청
    @Operation(summary = "가입 요청", description = "가계부에 가입되지 않은 사용자가 가계부 가입을 요청한다.")
    @PostMapping("/ledger/{ledgerId}/join")
    public ResponseEntity<ReturnIdDTO> createJoin(@PathVariable Long ledgerId, @AuthenticationPrincipal CustomUserDetails userDetails){

        // 가입 요청 생성
        ReturnIdDTO joinId = joinService.createJoin(ledgerId, userDetails.getId());

        // 가입 요청 알림 전송
        String ledgerOwnerUsername = ledgerService.getLedger(ledgerId).getLedgerOwnerUsername();
        String memberName = memberService.getMember(userDetails.getUsername()).getName();
        System.out.println("ledger 소유자 이름: " + ledgerOwnerUsername);
        System.out.println("멤버 이름: " + memberName);

        notificationService.sendJoinNotification(ledgerOwnerUsername, memberName);

        return ResponseEntity.ok(joinId);
    }

    // 가입 요청 목록 조회
    @Operation(summary = "가입 요청 조회", description = "가계부 관리자가 외부 사용자로부터 받은 가입 요청을 조회한다.")
    @GetMapping("/ledger/{ledgerId}/join")
    public ResponseEntity<List<JoinDTO.RequestData>> getJoin(@PathVariable Long ledgerId, @AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(joinService.getJoinData(ledgerId, userDetails.getId()));
    }

    // 참여 요청에 대한 응답 (수락 / 거절)
    @Operation(summary = "가입 요청 응답", description = "가계부 관리자가 외부 사용자로부터 받은 가입 요청을 수락/거절 한다.")
    @PostMapping("/ledger/{ledgerId}/join/{joinId}")
    public ResponseEntity<?> responseJoin(@PathVariable Long ledgerId, @PathVariable Long joinId, @RequestBody JoinDTO.Response res, @AuthenticationPrincipal CustomUserDetails userDetails){

        // 가입 요청에 대한 응답
        joinService.responseJoin(ledgerId, joinId, res, userDetails.getId());

        // 가입 요청에 대한 응답 알림 전송
        String memberUsername = joinService.getRequestMemberUsername(joinId);
        String ledgerName = ledgerService.getLedger(ledgerId).getLedgerName();
        boolean isAccepted = joinService.getResponseAction(joinId);

        System.out.println("member 유저네임: " + memberUsername);
        System.out.println("가계부 이름: " + ledgerName);
        System.out.println("수락 여부: " + isAccepted);
        notificationService.sendJoinResponseNotification(
                memberUsername,
                ledgerName,
                isAccepted
        );

        return ResponseEntity.ok(new ResponseDTO("가입 요청에 대한 응답이 처리되었습니다."));
    }

}
