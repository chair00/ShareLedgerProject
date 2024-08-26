package com.example.demo.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import jdk.jfr.Threshold;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 이메일 중복 체크
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 회원가입
    public void signUp(MemberDTO.SignUp req) {
        memberRepository.save(req.toEntity());
    }

    public Member login(MemberDTO.Login req){
        Member findMember = memberRepository.findByEmail(req.getEmail());
        return findMember;
    }

}
