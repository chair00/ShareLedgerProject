package com.example.demo.jwt;

import com.example.demo.dto.signUp.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String email = null;
        String password = null;

        if ("application/json".equals(request.getContentType())) {
            try {
                // ObjectMapper를 이용해 JSON을 Map으로 변환
                // ObjectMapper는 Jackson 라이브러리 클래스로, JSON <> Java 객체 변환 기능 제공
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> jsonRequest = mapper.readValue(request.getInputStream(), Map.class);
                email = jsonRequest.get("email");
                password = jsonRequest.get("password");
            } catch (IOException e) {
                throw new AuthenticationServiceException("Request 파싱 실패", e);
            }
        } else {
            email = request.getParameter("email");
            password = request.getParameter("password");
        }

        // AuthenticationManager로 넘기기 위해서는 token에 담아야함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공시 실행하는 메소드(jwt 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Long id = customUserDetails.getId();
        System.out.println("In LoginFilter - id : " + id);

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends  GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(id, username, role, 60*60*1000L); // 1시간

        response.addHeader("Authorization", "Bearer " + token);

        // 응답 본문(JSON) 추가
        String jsonResponse = String.format(
                "{ \"message\": \"Login successful\", \"token\": \"%s\", \"username\": \"%s\", \"role\": \"%s\" }",
                token, username, role
        );

        response.getWriter().write(jsonResponse);
    }

    // 로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }
}
