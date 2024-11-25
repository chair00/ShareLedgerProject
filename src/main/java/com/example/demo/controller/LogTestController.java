package com.example.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {
    private static final Logger logger = LoggerFactory.getLogger(LogTestController.class);

    @Value("${KAKAO_CLIENT_ID}")
    private String kakaoClientId;

    @GetMapping("/test-log")
    public String testLog() {
        logger.info("KAKAO_CLIENT_ID: {}", kakaoClientId);
        return "Logged KAKAO_CLIENT_ID!";
    }
}