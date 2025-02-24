package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.dto.signUp.CustomUserDetails;
import com.example.demo.service.LedgerMemberService;
import com.example.demo.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor

@Tag(name = "계정(멤버)", description = "계정(멤버) API")
public class MemberController {

    private final MemberService memberService;
    private final LedgerMemberService ledgerMemberService;

    ResponseDTO res = new ResponseDTO("성공");

    // 회원가입
    @Operation(summary = "회원가입", description = "사용자가 계정을 생성한다.")
    @PostMapping("/signup")
    public ResponseEntity<ReturnIdDTO> signup(@RequestBody MemberDTO.SignUp req) throws Exception{

        ReturnIdDTO memberId = memberService.signUp(req);
        return ResponseEntity.ok(memberId);
    }

    // 모바일 OAuth2 회원가입
    @Operation(summary = "모바일 OAuth2 회원가입", description = "(모바일)기존에 가입된 소셜 로그인 계정을 통해 사용자가 계정을 생성한다.")
    @PostMapping("/oauth2/signup")
    public ResponseEntity<TokenDTO> oauth2Login(@RequestBody MemberDTO.OAuth2Login req) throws Exception{

        TokenDTO token = memberService.oauth2Login(req);
        return ResponseEntity.ok(token);
    }

    // 로그인
    // 현재 동작 안하는 controller (security 에서 알아서 동작시킴)
    @Operation(summary = "로그인", description = "사용자가 생성된 계정으로 로그인한다." +
            " / email과 password를 json 형식으로 입력하면 로그인이 됨."
            + " / 응답 헤더에 토큰 반환(Authorization 필드 확인)"
            + " / swagger에서는 동작 안함.(에러뜸) 직접 앱/웹에서 테스트해야함")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO.Login req){
        return ResponseEntity.ok(res);
    }

    // 로그아웃
    @Operation(summary = "로그아웃", description = "로그인된 계정에서 로그아웃한다.")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok(res);
    }

    // 탈퇴
    // 가계부가 남아 있을 경우 + 그 안에 다른 멤버도 있을 경우 -> 가계부 완전 삭제 / 가계부 owner 권한 위임 할지 선택해야함
    // 가계부가 남아 있는데 안에 멤버가 없을 경우는 그냥 삭제. (아님 다 삭제할건지 메시지로 물어보든가..)
    @Operation(summary = "탈퇴", description = "사용자가 계정을 탈퇴한다.")
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody MemberDTO.PasswordVerifyRequest request){

        Long memberId = userDetails.getId();

        boolean isMatch = memberService.checkPassword(memberId, request.getPassword());
        if(isMatch) {
            memberService.deleteMember(memberId);
            return ResponseEntity.ok(new ResponseDTO("회원 탈퇴에 성공했습니다."));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("회원 탈퇴에 실패했습니다. (사유; 비밀번호 불일치)"));
    }

    // 계정 정보 조회
    @Operation(summary = "계정 정보 조회", description = "사용자가 회원가입 때 기입한 정보를 조회한다.")
    @GetMapping("/member")
    public ResponseEntity<?> showMemberInfo() {return ResponseEntity.ok(res);}

    // 계정 정보 수정
    @Operation(summary = "계정 정보 수정", description = "사용자가 회원가입 때 기입한 정보를 수정한다.")
    @PutMapping("/member")
    public ResponseEntity<?> updateMemberInfo() {return ResponseEntity.ok(res);}

    // 가계부 목록 조회
    @Operation(summary = "가계부 목록 조회", description = "사용자가 가입한 가계부 목록을 조회한다.")
    @GetMapping("/member/ledger-list")
    public ResponseEntity<List<LedgerDTO.ResponseDTO>> showLedgerList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<LedgerDTO.ResponseDTO> ledgers = ledgerMemberService.findLedgerDTOByMemberId(userDetails.getId());
        return ResponseEntity.ok(ledgers);
    }

    // 프로필 이미지 조회
    @Operation(summary = "프로필 조회", description = "사용자가 등록한 프로필을 조회한다.")
    @GetMapping("/member/profile-image")
    public ResponseEntity<MemberDTO.ProfileImage> showProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getId();
        String imageUrl = memberService.getProfileImage(memberId);

        return ResponseEntity.ok(new MemberDTO.ProfileImage(imageUrl));
    }

    // 프로필 이미지 등록
    @Operation(summary = "프로필 등록", description = "사용자가 프로필을 등록한다.")
    @PostMapping("/member/profile-image")
    public ResponseEntity<?> uploadProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("file") MultipartFile file) throws IOException {

        Long memberId = userDetails.getId();

        memberService.uploadProfileImage(memberId, file);
        return ResponseEntity.ok(res);
    }

    // 프로필 이미지 삭제
    @Operation(summary = "프로필 이미지 삭제", description = "사용자가 등록한 프로필을 삭제한다.")
    @PutMapping("/member/profile-image")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getId();
        memberService.deleteProfileImage(memberId);

        return ResponseEntity.ok(res);
    }

    // 멤버 검색
    @Operation(summary = "멤버 검색", description = "username으로 멤버를 검색한다.")
    @GetMapping("/member/search")
    public ResponseEntity<List<MemberDTO.Response>> searchMember(@RequestParam String username) {

        List<MemberDTO.Response> members = memberService.findMembers(username);
        return ResponseEntity.ok(members);
    }

    // 비밀번호 재확인
    @Operation(summary = "비밀번호 재확인", description = "회원 정보 수정 시 비밀번호를 재확인할 때 사용한다.")
    @PostMapping("/password/verify")
    public ResponseEntity<ResponseDTO> verifyPassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody MemberDTO.PasswordVerifyRequest request) {

        boolean isMatch = memberService.checkPassword(userDetails.getId(), request.getPassword());
        if (isMatch) {
            return ResponseEntity.ok(new ResponseDTO("비밀번호가 일치합니다."));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("비밀번호가 일치하지 않습니다."));
    }
}
