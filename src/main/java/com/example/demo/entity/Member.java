package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자는 사용x

    private String email; // 로그인 시 사용하는 id
    private String name;
    private String password;
    private String nickname;

    private MemberRole role; // 공유 가계부 전체 시스템에 대한 권한 분류(관리자 계정은 하나, 나머지는 전부 사용자)
    // 이미지
    // 생성일
    // 수정일
}
