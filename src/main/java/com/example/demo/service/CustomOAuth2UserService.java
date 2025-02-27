package com.example.demo.service;

import com.example.demo.dto.signUp.CustomOAuth2User;
import com.example.demo.dto.signUp.GoogleResponse;
import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.signUp.KakaoResponse;
import com.example.demo.entity.Member;
import com.example.demo.dto.signUp.NaverResponse;
import com.example.demo.dto.signUp.OAuth2Response;
import com.example.demo.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository userRepository;

    public CustomOAuth2UserService(MemberRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("oAuthUser: " + oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if(registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else {

            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        System.out.println("In CustomOAuth2UserService: existData 선언 전");
        Member existData = userRepository.findByUsername(username)
                .orElse(null);

        System.out.println("In CustomOAuth2UserService: existData 선언 후");

        if (existData == null) {  // 가입하지 않은 회원일 경우 -> 가입

            System.out.println("In CustomOAuth2UserService: 가입 시작");

            // 가입
            Member userEntity = new Member();
            userEntity.setUsername(username);
            userEntity.setProvider(oAuth2Response.getProvider());
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setName(oAuth2Response.getName());
            userEntity.setRole("ROLE_USER");

            userRepository.save(userEntity);

            System.out.println("In CustomOAuth2UserService: 가입 완료");
        }
        else { // 가입한 회원일 경우 -> 정보 수정

            // 회원 정보 변경 -> 해당 소셜의 정보가 변경되었을 경우. username은 변경 안되므로 이 프로그램에 딱히 영향을 끼치진 않음
            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            userRepository.save(existData);
        }

        // 로그인 진행. Provider한테 넘김

        System.out.println("In CustomOAuth2UserService: 로그인 시작");
        MemberDTO.OAuth2Login userDTO = new MemberDTO.OAuth2Login();
        userDTO.setUsername(username);
        userDTO.setName(oAuth2Response.getName());
//        userDTO.setRole("ROLE_USER");

        return new CustomOAuth2User(userDTO);
    }
}

