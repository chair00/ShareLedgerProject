package com.example.demo.controller;

import com.example.demo.dto.LedgerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class MemberController {
    // 회원가입
    @PostMapping("/signin")
    public String signin(){
        return "회원가입";
    }

    // 로그인
    @PostMapping("/login")
    public String login(){
        return "로그인";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(){
        return "로그인";
    }

    // 탈퇴
    @PostMapping("/withdraw")
    public String withdraw(){
        return "탈퇴";
    }

}
