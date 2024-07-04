package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class LedgerMemberController {
    // 회원 목록 조회
    @GetMapping("/ledger/{ledger_id}/member")
    public ResponseEntity<?> showLedgerMemberList(){return ResponseEntity.ok().body("회원 목록 조회");}

    // 회원 삭제
    @DeleteMapping("/ledger/{ledger_id}/member")
    public ResponseEntity<?> deleteLedgerMember(){return ResponseEntity.ok().body("회원 삭제");}

    // 가계부 탈퇴
    @PostMapping("/ledger/{ledger_id}/withdraw")
    public ResponseEntity<?> withdrawLedger(){return ResponseEntity.ok().body("가계부 탈퇴");}

    // 권한 수정
    @PutMapping("/ledger/{ledger_id}/member")
    public ResponseEntity<?> updateAuth(){return ResponseEntity.ok().body("권한 수정");}

    // 회원 초대
    @PostMapping("/ledger/{ledger_id}/invite")
    public ResponseEntity<?> inviteMember(){return ResponseEntity.ok().body("회원 초대");}

    // 초대 요청 조회
    @GetMapping("/member/invite")
    public ResponseEntity<?> showInvite(){return ResponseEntity.ok().body("초대 요청 조회");}

    // 초대 요청 수락
    @PostMapping("/member/invite/{request_id}/accept")
    public ResponseEntity<?> acceptInvite(){return ResponseEntity.ok().body("초대 요청 수락");}

    // 초대 요청 거절
    @PostMapping("/member/invite/{request_id}/reject")
    public ResponseEntity<?> rejectInvite(){return ResponseEntity.ok().body("초대 요청 거절");}

    // 가계부 이름/ 아이디 조회
    @GetMapping("/ledger")
    public ResponseEntity<?> searchLedger(){return ResponseEntity.ok().body("가계부 이름 또는 아이디 조회");}

    // 참여 요청
    @PostMapping("/member/join")
    public ResponseEntity<?> joinLedger(){return ResponseEntity.ok().body("참여 요청");}

    // 참여 요청 조회
    @GetMapping("/ledger/{ledger_id}/join")
    public ResponseEntity<?> showJoin(){return ResponseEntity.ok().body("참여 요청 조회");}

    // 참여 요청 수락
    @PostMapping("/ledger/{ledger_id}/join/{request_id}/accept")
    public ResponseEntity<?> acceptJoin(){return ResponseEntity.ok().body("참여 요청 수락");}

    // 참여 요청 거절
    @PostMapping("/ledger/{ledger_id}/join/{request_id}/reject")
    public ResponseEntity<?> rejectJoin(){return ResponseEntity.ok().body("참여 요청 거절");}

}
