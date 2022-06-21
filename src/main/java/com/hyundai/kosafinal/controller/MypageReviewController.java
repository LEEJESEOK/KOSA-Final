package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.MypageReviewDTO;
import com.hyundai.kosafinal.domain.ReplyDTO;
import com.hyundai.kosafinal.service.MypageReviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/mypage/review")
public class MypageReviewController {

    // 필요한 서비스 자동 주입
    @Autowired
    private MypageReviewService service;

    // 1:1 문의 게시글 목록 조회
    @GetMapping("/all")
    public ResponseEntity<List<MypageReviewDTO>> getMypageReviewList() {
        ResponseEntity<List<MypageReviewDTO>> entry = null;
        entry = new ResponseEntity<List<MypageReviewDTO>>(service.getList("TEST1"), HttpStatus.OK);
        log.info(entry);

        return entry;
    }

    // 1:1 문의 작성
    @PostMapping("/register")
    public boolean insert(@RequestBody MypageReviewDTO dto) {
        dto.setUserEmail("TEST1");
        log.info(dto);
        service.insert(dto);
        log.info("INSERT 완료 : " + dto);

        return true;
    }

    // 1:1 문의 답글 조회
    @GetMapping("/reply/{id}")
    public ResponseEntity<ReplyDTO> getMypageReviewReply(@PathVariable int id) {
        ResponseEntity<ReplyDTO> entry = null;
        entry = new ResponseEntity<ReplyDTO>(service.getReply(id), HttpStatus.OK);
        log.info(entry);

        return entry;
    }
}
