package com.example.demo.controller;

import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.jwt.TestTokenGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@Tag(name = "토큰", description = "테스트용 토큰을 발급한다.")
public class TestTokenController {

    private final TestTokenGenerator testTokenGenerator;

    @Autowired
    public TestTokenController(TestTokenGenerator testTokenGenerator) {
        this.testTokenGenerator = testTokenGenerator;
    }

    @Operation(summary = "토큰 확인", description = "테스트용 토큰을 확인한다.")
    @GetMapping("/token")
    public TokenDTO showGeneratedToken() {

        String token = "Bearer " + testTokenGenerator.getTestToken();
        return new TokenDTO(token);
    }
}