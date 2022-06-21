package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.MypageReviewDTO;
import com.hyundai.kosafinal.service.MypageReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    // 필요한 서비스 자동주입
    @Autowired
    private MypageReviewService service;

    // 1:1 문의 게시판 목록 페이지 이동
    @GetMapping("/mypage/review/list")
    public String goMypageReviewList() {
        return "mypage/reviewList";
    }

    // 1:1 문의 게시판 작성 페이지 이동
    @GetMapping("/mypage/review/insert")
    public String goMypageReviewForm() {
        return "mypage/reviewInsert";
    }

    // 1:1 문의 게시판 작성 완료 페이지 이동
    @GetMapping("/mypage/review/complete")
    public String goMypageReviewComplete() {
        return "mypage/reviewInsertComplete";
    }
}
