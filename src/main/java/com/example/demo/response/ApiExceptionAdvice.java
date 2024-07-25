//package com.example.demo.response;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class ApiExceptionAdvice {
//    @ExceptionHandler(ArithmeticException.class)
//    public ResponseEntity<ApiExceptionEntity> exceptionHandler(ArithmeticException e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//    }
//}
