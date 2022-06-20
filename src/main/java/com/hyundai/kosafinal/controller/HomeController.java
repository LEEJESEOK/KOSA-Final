package com.hyundai.kosafinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/mypage/reviewList")
    public String goMypageReviewList() {
        return "mypage/reviewList";
    }

    @GetMapping("/mypage/reviewInsert")
    public String goMypageReviewForm() {
        return "mypage/reviewInsert";
    }

}
