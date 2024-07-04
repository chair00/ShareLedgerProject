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

}
