package com.example.demo.config;


import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ServerInitializer {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public ServerInitializer(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ApplicationRunner initializeDefaultAdminAccount() {
        return args -> {

            boolean adminExists = memberRepository.existsByUsername("TEST");
            Member test = new Member();
            if (!adminExists) {
                // 관리자 계정 생성
                test.setUsername("TEST"); // 고정된 username
                test.setEmail("TEST");
                test.setPassword(passwordEncoder.encode("TEST")); // 고정된 password
                test.setRole("ROLE_ADMIN"); // 관리자 권한 설정

                memberRepository.save(test);

            } else {
                test = memberRepository.findByUsername("TEST");
            }
        };
    }
}
