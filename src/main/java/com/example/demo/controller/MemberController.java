package com.example.demo.controller;


import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.response.ApiResult;
import com.example.demo.service.MemberService;
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

    private final MemberService memberService;

    ResponseDTO res = new ResponseDTO("성공");

    // 회원가입
    @Operation(summary = "회원가입", description = "사용자가 계정을 생성한다.")
    @PostMapping("/signup")
    public ResponseEntity<ReturnIdDTO> signup(@RequestBody MemberDTO.SignUp req) throws Exception{

        if (!req.getPassword().equals(req.passwordCheck)) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        ReturnIdDTO memberId = memberService.signUp(req);
        return ResponseEntity.ok(memberId);
    }

    // 로그인
    @Operation(summary = "로그인", description = "사용자가 생성된 계정으로 로그인한다." +
            " / email과 password를 json 형식으로 입력하면 로그인이 됨."
            + " / 응답 헤더에 토큰 반환(Authorization 필드 확인)"
            + " / swagger에서는 동작 안함.(에러뜸) 직접 앱/웹에서 테스트해야함")
//    @PostMapping("/login")
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
    @Operation(summary = "탈퇴", description = "사용자가 계정을 탈퇴한다.")
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(){
        return ResponseEntity.ok(res);
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
    public ResponseEntity<?> showLedgerList() {return ResponseEntity.ok(res);}

    // 프로필 등록
    @Operation(summary = "프로필 등록", description = "사용자가 프로필을 등록한다.")
    @PostMapping("/member/profile")
    public ResponseEntity<?> createProfile() {return ResponseEntity.ok(res);}

    // 프로필 조회
    @Operation(summary = "프로필 조회", description = "사용자가 등록한 프로필을 조회한다.")
    @GetMapping("/member/profile")
    public ResponseEntity<?> showProfile() {return ResponseEntity.ok(res);}

    // 프로필 수정
    @Operation(summary = "프로필 수정", description = "사용자가 등록한 프로필을 수정한다.")
    @PutMapping("/member/profile")
    public ResponseEntity<?> updateProfile() {return ResponseEntity.ok(res);}

}
