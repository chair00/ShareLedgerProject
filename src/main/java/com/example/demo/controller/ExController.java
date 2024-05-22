package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExController {
    @GetMapping("/")
    public String helloWorld() {
        return "Hello World!!";
    }
}
