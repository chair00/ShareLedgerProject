package com.example.demo.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.entity.LedgerMember;
import com.example.demo.entity.Member;
import com.example.demo.jwt.JWTUtil;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final LedgerMemberService ledgerMemberService;
    private final LedgerService ledgerService;

    private static final String UPLOAD_DIR = "uploads/";
    private static final String BASE_URL = "http://localhost:8080/uploads/";

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
            member = memberRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("멤버 username이 존재하지 않습니다."));
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
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("멤버 username이 존재하지 않습니다."));

        return new MemberDTO.Response(member.getUsername(), member.getEmail(), member.getName());
    }

    public List<MemberDTO.Response> findMembers(String username) {
        List<Member> members = memberRepository.findByUsernameContaining(username);

        return members.stream()
                .map(member -> new MemberDTO.Response(member.getUsername(), member.getEmail(), member.getName()))
                .collect(Collectors.toList());
    }

    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버 id가 존재하지 않습니다."));

        return member;
    }

    public boolean checkPassword(Long id, String rawPassword) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("멤버 id가 존재하지 않습니다."));

        return passwordEncoder.matches(rawPassword, member.getPassword());
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버 id가 존재하지 않습니다."));

        // 해당 회원의 모든 가계부 조회
        List<LedgerMember> ledgerMembers = ledgerMemberService.findByMemberId(memberId);

        for(LedgerMember ledgerMember : ledgerMembers) {
            // owner인 경우 권한 위임 또는 가계부 삭제
            if (ledgerMember.isOwner()) {
                // 권한 위임
                // ledgerMemberService.changeOwner(ledgerMember.getLedger(), newOwnerId);

                // 가계부 삭제
                ledgerService.deleteLedger(ledgerMember.getLedger().getId(), memberId);
            } else {
                // 일반 회원인 경우 ledgerMember 목록에서 삭제
                ledgerMemberService.deleteMemberFromLedger(ledgerMember);
            }
        }

        // 계정 삭제
        memberRepository.delete(member);
    }

    public String getProfileImage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버 id가 존재하지 않습니다."));

        return member.getProfileImgUrl();
    }

    @Transactional
    public void uploadProfileImage(Long memberId, MultipartFile file) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버 id가 존재하지 않습니다."));

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        Files.createDirectories(filePath.getParent()); // 폴더가 없으면 생성
        Files.write(filePath, file.getBytes());

        String imageUrl = BASE_URL + fileName;

        member.setProfileImgUrl(imageUrl);
        memberRepository.save(member);
    }

    @Transactional
    public void deleteProfileImage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버 id가 존재하지 않습니다."));

        String imageUrl = member.getProfileImgUrl();
        if (imageUrl != null) {
            String fileName = imageUrl.replace(BASE_URL, ""); // 저장된 파일명 추출
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            try {
                Files.deleteIfExists(filePath); // 파일 삭제
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete profile image", e);
            }
        }

        member.setProfileImgUrl(null);
        memberRepository.save(member);
    }

}
