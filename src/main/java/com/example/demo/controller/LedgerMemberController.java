package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor

@Tag(name = "가계부 멤버", description = "가계부-멤버 API")
public class LedgerMemberController {
    ResponseDTO res = new ResponseDTO("성공");
    // 회원 목록 조회
    @Operation(summary = "회원 목록 조회", description = "가계부에 가입된 회원 목록을 조회한다.")
    @GetMapping("/ledger/{ledger_id}/member")
    public ResponseEntity<?> showLedgerMemberList(){
        return ResponseEntity.ok(res);
    }

    // 회원 삭제
    @Operation(summary = "회원 삭제", description = "관리자가 가계부에 가입된 회원을 삭제한다.")
    @DeleteMapping("/ledger/{ledger_id}/member")
    public ResponseEntity<?> deleteLedgerMember(){
        return ResponseEntity.ok(res);
    }

    // 가계부 탈퇴
    @Operation(summary = "가계부 탈퇴", description = "가계부에 가입된 회원이 가계부를 탈퇴한다.")
    @PostMapping("/ledger/{ledger_id}/withdraw")
    public ResponseEntity<?> withdrawLedger(){
        return ResponseEntity.ok(res);
    }

    // 권한 수정
    @Operation(summary = "권한 수정", description = "관리자가 가계부에 가입된 회원의 권환을 수정한다.")
    @PutMapping("/ledger/{ledger_id}/member")
    public ResponseEntity<?> updateAuth(){
        return ResponseEntity.ok(res);
    }

    // 회원 초대
    @Operation(summary = "회원 초대", description = "관리자가 가계부에 가입되지 않은 사용자를 초대한다.")
    @PostMapping("/ledger/{ledger_id}/invite")
    public ResponseEntity<?> inviteMember(){
        return ResponseEntity.ok(res);
    }

    // 초대 요청 조회
    @Operation(summary = "초대 요청 조회", description = "사용자가 가계부에서 온 초대 요청을 조회한다.")
    @GetMapping("/member/invite")
    public ResponseEntity<?> showInvite(){
        return ResponseEntity.ok(res);
    }

    // 초대 요청 수락
    @Operation(summary = "초대 요청 수락", description = "사용자가 가계부에서 온 초대 요청을 수락한다.")
    @PostMapping("/member/invite/{request_id}/accept")
    public ResponseEntity<?> acceptInvite(){
        return ResponseEntity.ok(res);
    }

    // 초대 요청 거절
    @Operation(summary = "초대 요청 거절", description = "사용자가 가계부에서 온 초대 요청을 거절한다.")
    @PostMapping("/member/invite/{request_id}/reject")
    public ResponseEntity<?> rejectInvite(){
        return ResponseEntity.ok(res);
    }

    // 가계부 이름/ 아이디 조회
    @Operation(summary = "검색(가계부/사용자)", description = "가계부 이름 또는 사용자 id를 검색한다.")
    @GetMapping("/ledger")
    public ResponseEntity<?> searchLedger(){
        return ResponseEntity.ok(res);
    }

    // 참여 요청
    @Operation(summary = "참여 요청", description = "가계부에 가입되지 않은 사용자가 가계부 가입을 요청한다.")
    @PostMapping("/member/join")
    public ResponseEntity<?> joinLedger(){
        return ResponseEntity.ok(res);
    }

    // 참여 요청 조회
    @Operation(summary = "참여 요청 조회", description = "가계부 관리자가 외부 사용자로부터 받은 참여 요청을 조회한다.")
    @GetMapping("/ledger/{ledger_id}/join")
    public ResponseEntity<?> showJoin(){
        return ResponseEntity.ok(res);
    }

    // 참여 요청 수락
    @Operation(summary = "참여 요청 수락", description = "가계부 관리자가 외부 사용자로부터 받은 참여 요청을 수락한다.")
    @PostMapping("/ledger/{ledger_id}/join/{request_id}/accept")
    public ResponseEntity<?> acceptJoin(){
        return ResponseEntity.ok(res);
    }

    // 참여 요청 거절
    @Operation(summary = "참여 요청 거절", description = "가계부 관리자가 외부 사용자로부터 받은 참여 요청을 거절한다.")
    @PostMapping("/ledger/{ledger_id}/join/{request_id}/reject")
    public ResponseEntity<?> rejectJoin(){
        return ResponseEntity.ok(res);
    }

}
