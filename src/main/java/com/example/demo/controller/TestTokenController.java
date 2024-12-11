package com.example.demo.controller;

import com.example.demo.dto.TokenDTO;
import com.example.demo.entity.Member;
import com.example.demo.jwt.JWTUtil;
import com.example.demo.repository.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Tag(name = "토큰", description = "테스트용 토큰을 발급한다.")
public class TestTokenController {

    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "토큰 확인", description = "테스트용 계정의 토큰을 확인한다. g// email = TEST, username = TEST, password = TEST")
    @GetMapping("/token")
    public TokenDTO showGeneratedToken() {

        // 관리자 계정 조회
        Member test = memberRepository.findByUsername("TEST");

        // JWT 생성
        String token = "Bearer " + jwtUtil.createJwt(
                test.getId(),
                test.getUsername(),
                test.getRole(),
                1000L * 60 * 60 * 24 * 365 * 10 // 10년
        );


        return new TokenDTO(token);
    }
}