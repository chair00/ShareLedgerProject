package com.example.demo.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.dto.signUp.CustomOAuth2User;
import com.example.demo.entity.Member;
import com.example.demo.jwt.JWTUtil;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;

    // 이메일 중복 체크
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByUsername(email);
    }

    // 회원가입
    public ReturnIdDTO signUp(MemberDTO.SignUp req) {

        String email = req.getEmail();
        String password = req.getPassword();
        String name = req.getName();

        Boolean isExist = memberRepository.existsByUsername(email);

        if(isExist) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member data = new Member();
        data.setUsername(email);
        data.setEmail(email);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_USER");
        data.setName(name);

        return new ReturnIdDTO(memberRepository.save(data));
//        memberRepository.save(req.toEntity());
    }

    public TokenDTO oauth2Login(MemberDTO.OAuth2Login req) {

        // 사용자 존재 유무 확인
        String username = req.getUsername();
        System.out.println("username");

        Boolean isExist = memberRepository.existsByUsername(username);
        Member member = new Member();

        if(!isExist) {
            System.out.println("no Exist");
            member.setUsername(req.getUsername());
            member.setEmail(req.getEmail());
            member.setProvider(req.getProvider());
            member.setName(req.getName());
            member.setRole("ROLE_USER");

            memberRepository.save(member);
        } else {
            member = memberRepository.findByUsername(username);
        }

        Long id = member.getId();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(id, username, role, 1000 * 60 * 60 * 24L);
        String generatedToken = "Bearer " + token;

        return new TokenDTO(generatedToken);

    }

    public MemberDTO.Response getMember(String username) {
        Member member = memberRepository.findByUsername(username);

        return new MemberDTO.Response(member.getUsername(), member.getEmail(), member.getName());
    }

    public List<MemberDTO.Response> findMember(String username) {
        List<Member> members = memberRepository.findByUsernameContaining(username);

        return members.stream()
                .map(member -> new MemberDTO.Response(member.getUsername(), member.getEmail(), member.getName()))
                .collect(Collectors.toList());
    }

}
