package com.example.demo.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class TestTokenGenerator implements CommandLineRunner {

    private String testToken;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public void run(String... args) throws Exception {
        testToken = jwtUtil.generateTestToken();
    }

    public String getTestToken() {
        return testToken;
    }
}