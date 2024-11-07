package com.example.demo.controller;

import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.jwt.TestTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class TestTokenController {

    private final TestTokenGenerator testTokenGenerator;

    @Autowired
    public TestTokenController(TestTokenGenerator testTokenGenerator) {
        this.testTokenGenerator = testTokenGenerator;
    }

    @GetMapping("/token")
    public TokenDTO showGeneratedToken() {

        String token = "Bearer " + testTokenGenerator.getTestToken();
        return new TokenDTO(token);
    }
}