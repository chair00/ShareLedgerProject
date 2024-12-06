package com.example.demo.controller;

import com.example.demo.dto.JoinDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.dto.signUp.CustomUserDetails;
import com.example.demo.service.JoinService;
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

    // 참여 요청
    @Operation(summary = "참여 요청", description = "가계부에 가입되지 않은 사용자가 가계부 가입을 요청한다.")
    @PostMapping("/ledger/{ledgerId}/join")
    public ResponseEntity<ReturnIdDTO> createJoin(@PathVariable Long ledgerId, @AuthenticationPrincipal CustomUserDetails userDetails){

        return ResponseEntity.ok(joinService.createJoin(ledgerId, userDetails.getId()));
    }

    // 참여 요청 목록 조회
    @Operation(summary = "참여 요청 조회", description = "가계부 관리자가 외부 사용자로부터 받은 참여 요청을 조회한다.")
    @GetMapping("/ledger/{ledgerId}/join")
    public ResponseEntity<List<JoinDTO.RequestData>> getJoin(@PathVariable Long ledgerId, @AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(joinService.getJoinData(ledgerId, userDetails.getId()));
    }

    // 참여 요청에 대한 응답 (수락 / 거절)
    @Operation(summary = "참여 요청 응답", description = "가계부 관리자가 외부 사용자로부터 받은 참여 요청을 수락/거절 한다.")
    @PostMapping("/ledger/{ledgerId}/join/{requestId}")
    public ResponseEntity<?> responseJoin(@PathVariable Long ledgerId, @PathVariable Long requestId, @RequestBody JoinDTO.Response res, @AuthenticationPrincipal CustomUserDetails userDetails){
        joinService.responseJoin(ledgerId, requestId, res, userDetails.getId());
        return ResponseEntity.ok(new ResponseDTO("가입 요청에 대한 응답이 처리되었습니다."));
    }

}
