package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.MypageReviewDTO;
import com.hyundai.kosafinal.service.MypageReviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/mypage/review")
public class MypageReviewController {

    // 필요한 서비스 자동 주입
    @Autowired
    private MypageReviewService service;

    // 1:1 문의 게시글 목록 조회
    @GetMapping("")
    public void getMypageReviewList() {
        log.info(service.getList("TEST1"));
    }

    // 1:1 문의 게시글 조회
    @GetMapping("/{id}")
    public void getMypageReviewDetail() {
        log.info(service.getDetail(1));
    }

    // 1:1 문의 작성
    @PostMapping("/register")
    public void insert(MypageReviewDTO dto) {
        MypageReviewDTO dto2 = new MypageReviewDTO(0, "test", "content", 0,
                "대분류", "소분류", "test1@naver.com", 0, null);
        service.insert(dto);


    }


}
