package com.example.demo.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 이메일 중복 체크
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 회원가입
    public ReturnIdDTO signUp(MemberDTO.SignUp req) {

        String email = req.getEmail();
        String password = req.getPassword();
        String name = req.getName();

        Boolean isExist = memberRepository.existsByEmail(email);

        if(isExist) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member data = new Member();
        data.setEmail(email);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_USER");
        data.setName(name);

        return new ReturnIdDTO(memberRepository.save(data));
//        memberRepository.save(req.toEntity());
    }


//    public Member login(MemberDTO.Login req){
//        Member findMember = memberRepository.findByEmail(req.getEmail());
//        return findMember;
//    }

}
