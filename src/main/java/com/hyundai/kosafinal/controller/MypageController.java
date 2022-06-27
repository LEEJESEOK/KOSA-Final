package com.hyundai.kosafinal.controller;


import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/mypage")
public class MypageController {
    @Autowired
    MemberService service;

    @GetMapping("/modify") //회원정보 수정버튼 눌렀을때
    public void modify(MemberDTO member) {
        log.info("회원정보 수정 페이지 이동");
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
