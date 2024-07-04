package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class MemberController {
    // 회원가입
    @PostMapping("/signin")
    public ResponseEntity<?> signin(){
        return ResponseEntity.ok().body("회원가입");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok().body("로그인");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok().body("로그아웃");
    }

    // 탈퇴
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(){
        return ResponseEntity.ok().body("탈퇴");
    }

    // 계정 정보 조회
    @GetMapping("/member")
    public ResponseEntity<?> showMemberInfo() {return ResponseEntity.ok().body("계정정보조회");}

    // 계정 정보 수정
    @PutMapping("/member")
    public ResponseEntity<?> updateMemberInfo() {return ResponseEntity.ok().body("계정정보수정");}

    // 가계부 목록 조회
    @GetMapping("/member/ledger-list")
    public ResponseEntity<?> showLedgerList() {return ResponseEntity.ok().body("가계부 목록 조회");}

    // 프로필 등록
    @GetMapping("/member/profile")
    public ResponseEntity<?> createProfile() {return ResponseEntity.ok().body("프로필 등록");}

    // 프로필 조회
    @GetMapping("/member/profile")
    public ResponseEntity<?> showProfile() {return ResponseEntity.ok().body("프로필 조회");}

    // 프로필 수정
    @GetMapping("/member/profile")
    public ResponseEntity<?> updateProfile() {return ResponseEntity.ok().body("프로필 수정");}

}
