package com.example.demo.dto;

import com.example.demo.entity.Member;
import com.example.demo.entity.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
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
        @Schema(description = "비밀번호 확인")
        @NotBlank(message = "비밀번호를 재입력하세요.")
        public String passwordCheck;

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

    @Schema(description = "로그인 요청")
    @Getter
    public static class Login {
        @Schema(description = "이메일(로그인 id)")
        private String email;
        @Schema(description = "비밀번호")
        private String password;
    }
}
