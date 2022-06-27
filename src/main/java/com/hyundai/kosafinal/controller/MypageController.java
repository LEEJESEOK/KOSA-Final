package com.hyundai.kosafinal.controller;


import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/mypage")
public class MypageController {
    @Autowired
    MemberService service;

    @GetMapping("/modify")

    public void modify(Model model, Authentication authentication) {
        log.info("회원정보 수정 페이지 이동");
        Member2DTO member=service.findByEmail(authentication.getName());

        model.addAttribute("gender",member.getGender());
        model.addAttribute("tel",member.getTel());
        model.addAttribute("address1",member.getAddress1());
        model.addAttribute("address2",member.getAddress2());
        model.addAttribute("height",member.getHeight());
        model.addAttribute("weight",member.getWeight());
        model.addAttribute("zipcode",member.getZipcode());
        System.out.println(member.getBirth());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birth = simpleDateFormat.format(member.getBirth());
        System.out.println(birth);

        model.addAttribute("selYear",birth.substring(0,4));
        model.addAttribute("selMonth",birth.substring(5,7));
        model.addAttribute("selDay",birth.substring(8,10));
    }

    @GetMapping(value = {"", "/"}) //마이페이지 첫화면
    public String basic() {
        log.info("마이페이지 이동");
        return "mypage/basic";
    }

    // 1:1 문의 게시판 목록 페이지 이동
    @GetMapping("/reviewList")
    public void reviewList() {
        log.info("리뷰 목록 페이지 이동");
    }

    // 1:1 문의 게시판 작성 페이지 이동
    @GetMapping("/reviewInsert")
    public void reviewInsert() {
        log.info("리뷰 작성 페이지 이동");
    }

    // 1:1 문의 게시판 작성 완료 페이지 이동
    @GetMapping("/reviewInsertComplete")
    public void reviewInsertComplete() {
        log.info("리뷰 작성 완료 페이지 이동");
    }

}
