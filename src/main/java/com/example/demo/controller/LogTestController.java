package com.example.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {
    private static final Logger logger = LoggerFactory.getLogger(LogTestController.class);

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @GetMapping("/test-log")
    public String testLog() {
        logger.info("NAVER_CLIENT_ID: {}", naverClientId);
        logger.info("KAKAO_CLIENT_ID: {}", kakaoClientId);
        return "Logged CLIENT_ID!!";
    }
}