package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 인증이 안된 사용자들이 출입할 수있는 경로를 /auth/* 허용
// 그냥 주소가 루프페이지이면 index.jsp 허용
// static 이하에있는 /js/* /css/* /image/* 모두 허용

@Controller
public class UserController {
    
    @GetMapping("/auth/join_form")
    public String joinForm() {
        return "user/join_form";
    }

    @GetMapping("/auth/login_form")
    public String loginForm() {
        return "user/login_form";
    }
}
