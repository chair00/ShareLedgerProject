package com.example.demo.service;

import com.example.demo.dto.signUp.CustomUserDetails;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member userData = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("멤버 username이 존재하지 않습니다."));

        if(userData == null) {
            throw new UsernameNotFoundException("해당 username이 없음 : " + username);
        }

        return new CustomUserDetails(userData);
    }
}
