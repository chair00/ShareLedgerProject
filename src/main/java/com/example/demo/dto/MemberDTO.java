package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원 정보")
public class MemberDTO {

    @Getter
    @Setter
    @Schema(description = "회원가입 요청")
    public static class SignUp {
        @Schema(description = "이메일(로그인 id)")
        @NotBlank(message = "이메일을 입력하세요.")
        // 이메일 중복체크? 이메일 실제 확인->인증코드?
        private String email;

        @Schema(description = "비밀번호")
        @NotBlank(message = "비밀번호를 입력하세요.")
        private String password;

        @Schema(description = "사용자 이름")
        @NotBlank(message = "이름을 입력하세요.")
        private String name;

//        public Member toEntity() {
//            return Member.builder()
//                    .email(email)
//                    .password(password)
//                    .name(name)
//                    .role("ROLE_USER")
//                    .build();
//        }
    }

    @Schema(description = "OAuth2 회원가입")
    @Getter
    @Setter
    public static class OAuth2Login {
        private Long id;
        @Schema(description = "username = provider + \" \" + providerId -> 사용자 식별 시 사용")
        private String username;
        @Schema(description = "이메일")
        private String email;
        @Schema(description = "이름")
        private String name;
        @Schema(description = "권한")
        private String role;
        @Schema(description = "provider / ex. naver, google, kakao")
        private String provider;
    }

    @Schema(description = "로그인 요청")
    @Getter
    public static class Login {
        @Schema(description = "이메일(로그인 id)")
        private String email;
        @Schema(description = "비밀번호")
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "멤버 정보 응답")
    public static class Response {
        @Schema(description = "사용자 username")
        private String username;

        @Schema(description = "사용자 이메일")
        private String email;

        @Schema(description = "사용자 이름")
        private String name;

//        public Response(String username, String email, String name) {
//            this.username = username;
//            this.email = email;
//            this.name = name;
//        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "비밀번호 재확인")
    public static class PasswordVerifyRequest {
        @Schema(description = "password 입력")
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "프로필 이미지 요청/응답")
    public static class ProfileImage {
        @Schema(description = "이미지 url")
        private String url;
    }
}
