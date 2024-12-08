package com.example.demo.controller;

import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.entity.Member;
import com.example.demo.jwt.JWTUtil;
import com.example.demo.jwt.TestTokenGenerator;
import com.example.demo.repository.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "토큰 확인", description = "테스트용 토큰을 확인한다.")
    @GetMapping("/token")
    public TokenDTO showGeneratedToken() {

        Member admin = new Member();
        admin.setUsername("ADMIN"); // 고정된 username
        admin.setPassword(passwordEncoder.encode("ADMIN")); // 고정된 password
        admin.setRole("ROLE_ADMIN"); // 예: 관리자 권한

        memberRepository.save(admin);

//        // 관리자 계정 조회
//        Member admin = memberRepository.findByUsername("ADMIN");


        // JWT 생성
        String token = "Bearer " + jwtUtil.createJwt(
                admin.getId(),
                admin.getUsername(),
                admin.getRole(),
                1000L * 60 * 60 * 24 * 365 * 10 // 10년
        );

        return new TokenDTO(token);
    }
}