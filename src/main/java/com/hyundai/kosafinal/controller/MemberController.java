package com.hyundai.kosafinal.controller;


import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService service;


    @GetMapping("/admin") // 관리자 권한이 있는 사용자만이 접근할 수 있다.
    public void Admin() {
        log.info("Admin.....");

    }

    @GetMapping("/login") //로그인 페이지 이동
    public void login() {
        log.info("usual_login......");
    }

    @GetMapping("/signUp") //회원 가입 폼으로 이동
    public String signUp() {
        return "member/signUp";
    }

    @PostMapping("/delete")
    public String delete(String email){
        log.info("이승연"+email);
        service.deleteMember(email);

        return "redirect:/logout";
    }

    @GetMapping("")
    public String update_view(){ //수정 입력 양식 html 로 이동
        log.info("update_view");
        return "login/Modify";
    }

    @PostMapping("/modify") //회원정보 수정버튼 눌렀을때
    public String update(MemberDTO member){
        log.info("이승연"+member);

        service.updateMember(member);
        return "redirect:/logout";
    }
}
