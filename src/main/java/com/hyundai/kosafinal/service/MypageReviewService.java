package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.MypageReviewDTO;
import com.hyundai.kosafinal.domain.ReplyDTO;
import com.hyundai.kosafinal.entity.SearchMypageReviewCriteria;
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

    // 게시글 답글 추가
    public void insertReply(ReplyDTO dto);

    // 게시글 상태 변경
    public void updateStatus(int id);

    // 백오피스 게시글 검색 결과 개수
    public int searchMypageReviewCount(SearchMypageReviewCriteria criteria);

    // 백오피스 게시글 검색
    public List<MypageReviewDTO> searchMypageReview(SearchMypageReviewCriteria criteria);
}
