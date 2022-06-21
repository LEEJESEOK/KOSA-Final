package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.MypageReviewDTO;
import com.hyundai.kosafinal.domain.ReplyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MypageReviewService {

    // 게시글 목록 조회
    public List<MypageReviewDTO> getList(String userEmail);

    // 게시글 조회
    public MypageReviewDTO getDetail(int id);

    // 게시글 작성
    public void insert(MypageReviewDTO dto);

    // 게시글 답글 조회
    public ReplyDTO getReply(int id);
}
