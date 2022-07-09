package com.hyundai.kosafinal.controller;


import com.hyundai.kosafinal.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService service;

    @GetMapping("/login") //로그인 페이지 이동
    public void login() {
        log.info("usual_login......");
    }

    @GetMapping("/signUpComplete")
    public void signUpComplete() {
        log.info("signUpcomplete......");
    }

    @GetMapping("/signUp") //회원 가입 폼으로 이동
    public String signUp() {
        return "member/signUp";
    }

}
