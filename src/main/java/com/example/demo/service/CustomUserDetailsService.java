package com.example.demo.service;

import com.example.demo.dto.CustomUserDetails;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("email: " + email);
        Member userData = memberRepository.findByEmail(email);

        if(userData == null) {
            throw new UsernameNotFoundException("해당 이메일이 없음 : " + email);
        }

        return new CustomUserDetails(userData);
    }
}
