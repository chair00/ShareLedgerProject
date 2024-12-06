package com.example.demo.dto.signUp;

import com.example.demo.dto.MemberDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final MemberDTO.OAuth2Login userDTO;
    public CustomOAuth2User(MemberDTO.OAuth2Login userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    // google, naver 등 소셜 사이트마다 형식이 달라 획일화하기 어려워 구현 진행 안함
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDTO.getRole();
            }
        });

        return collection;
    }

    public Long getId() {
        return userDTO.getId();
    }

    @Override
    public String getName() {
        return userDTO.getName();
    }

    public String getUsername() {
        return userDTO.getUsername();
    }
}
