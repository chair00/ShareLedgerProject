package com.example.demo.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E002"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E003");
//    EXISTS_LOGINID(HttpStatus.BAD_REQUEST, "B001", "중복된 아이디 입니다."),
//    VALIDATION_ID(HttpStatus.BAD_REQUEST, "B002", "아이디는 6~15자 소문자 영어로 시작, 영어 소문자와 숫자 최소 한가지씩 조합해야합니다."),
//    VALIDATION_PWD(HttpStatus.BAD_REQUEST, "B003", "비밀번호는 8~16자 영대소문자, 숫자, 특수문자를 최소 한가지씩 조합해야합니다."),
//    SECURITY_01(HttpStatus.UNAUTHORIZED, "S0001", "권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
