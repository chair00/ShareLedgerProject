package com.example.demo.controller;


import com.example.demo.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor

@Tag(name = "계정", description = "계정 API")
public class MemberController {
    // 회원가입
    @Operation(summary = "회원가입", description = "사용자가 계정을 생성한다.")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(){
        return ResponseEntity.ok().body(ApiResult.builder().message("회원가입").build());
    }

    // 로그인
    @Operation(summary = "로그인", description = "사용자가 생성된 계정으로 로그인한다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok().body(ApiResult.builder().message("로그인").build());
    }

    // 로그아웃
    @Operation(summary = "로그아웃", description = "로그인된 계정에서 로그아웃한다.")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok().body(ApiResult.builder().message("로그아웃").build());
    }

    // 탈퇴
    @Operation(summary = "탈퇴", description = "사용자가 계정을 탈퇴한다.")
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(){
        return ResponseEntity.ok().body(ApiResult.builder().message("탈퇴").build());
    }

    // 계정 정보 조회
    @Operation(summary = "계정 정보 조회", description = "사용자가 회원가입 때 기입한 정보를 조회한다.")
    @GetMapping("/member")
    public ResponseEntity<?> showMemberInfo() {return ResponseEntity.ok().body(ApiResult.builder().message("계정정보조회").build());}

    // 계정 정보 수정
    @Operation(summary = "계정 정보 수정", description = "사용자가 회원가입 때 기입한 정보를 수정한다.")
    @PutMapping("/member")
    public ResponseEntity<?> updateMemberInfo() {return ResponseEntity.ok().body(ApiResult.builder().message("계정정보수정").build());}

    // 가계부 목록 조회
    @Operation(summary = "가계부 목록 조회", description = "사용자가 가입한 가계부 목록을 조회한다.")
    @GetMapping("/member/ledger-list")
    public ResponseEntity<?> showLedgerList() {return ResponseEntity.ok().body(ApiResult.builder().message("가계부목록조회").build());}

    // 프로필 등록
    @Operation(summary = "프로필 등록", description = "사용자가 프로필을 등록한다.")
    @PostMapping("/member/profile")
    public ResponseEntity<?> createProfile() {return ResponseEntity.ok().body(ApiResult.builder().message("프로필 등록").build());}

    // 프로필 조회
    @Operation(summary = "프로필 조회", description = "사용자가 등록한 프로필을 조회한다.")
    @GetMapping("/member/profile")
    public ResponseEntity<?> showProfile() {return ResponseEntity.ok().body(ApiResult.builder().message("프로필 조회").build());}

    // 프로필 수정
    @Operation(summary = "프로필 수정", description = "사용자가 등록한 프로필을 수정한다.")
    @PutMapping("/member/profile")
    public ResponseEntity<?> updateProfile() {return ResponseEntity.ok().body(ApiResult.builder().message("프로필 수정").build());}

}
