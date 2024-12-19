package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일 중복 검사 => 이미 존재할 경우 true 리턴
    boolean existsByUsername(String username);

    Member findByUsername(String username);

    List<Member> findByUsernameContaining(String username);
}
