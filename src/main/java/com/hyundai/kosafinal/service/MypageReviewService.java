package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.MypageReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MypageReviewService {

    // 게시글 목록 조회
    public List<MypageReviewDTO> getList(String userEmail);

    // 게시글 조회
    public MypageReviewDTO getDetail(int id);

    // 게시글 작성
    public void insert(MypageReviewDTO dto);
}
