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
    // -- 추가할 기능 --
    // 가계부 권한 변경
    // 가계부 owner 위임 기능

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

//    // 회원 초대
//    @Operation(summary = "회원 초대", description = "관리자가 가계부에 가입되지 않은 사용자를 초대한다.")
//    @PostMapping("/ledger/{ledger_id}/invite")
//    public ResponseEntity<?> inviteMember(){
//        return ResponseEntity.ok(res);
//    }

    // 가계부 이름/ 아이디 조회
    @Operation(summary = "검색(가계부/사용자)", description = "가계부 이름 또는 사용자 id를 검색한다.")
    @GetMapping("/ledger")
    public ResponseEntity<?> searchLedger(){
        return ResponseEntity.ok(res);
    }



}
