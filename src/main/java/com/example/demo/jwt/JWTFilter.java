package com.example.demo.jwt;

import com.example.demo.dto.signUp.CustomUserDetails;
import com.example.demo.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }

        System.out.println("authorization now");

        String token = authorization.split(" ")[1];

        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        String id = jwtUtil.getSub(token);
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // Check if id is null or empty
        if (id == null || id.isEmpty()) {
            System.out.println("Token subject (id) is null or empty");
            filterChain.doFilter(request, response);
            return;
        }

        // Validate if id is a valid number
        Long memberId;
        try {
            memberId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format in token: " + id);
            filterChain.doFilter(request, response);
            return;
        }

        // SecurityContextHolder에 저장하기 위해 token의 payload에 저장된 값을 통해 사용자 정보를 세션으로 만든다.
        Member member = new Member();
        member.setId(memberId);
        member.setUsername(username);
        member.setPassword("temppassword"); //세션 등록만 하면 돼서 비번은 아무거나 넣어준다.
        member.setRole(role);

        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        System.out.println("In JWTFilter - getUsername : "+customUserDetails.getUsername());

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
