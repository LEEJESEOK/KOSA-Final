package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.MypageReviewDTO;
import com.hyundai.kosafinal.domain.ReplyDTO;
import com.hyundai.kosafinal.entity.SearchMypageReviewCriteria;
import com.hyundai.kosafinal.mapper.userorder.MypageReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MypageReviewServiceImpl implements MypageReviewService {

    // DAO 자동 주입
    @Autowired
    private MypageReviewMapper mapper;

    // 게시글 목록 조회
    @Override
    public List<MypageReviewDTO> getList(String userEmail) {
        return mapper.getList(userEmail);
    }

    // 게시글 조회
    @Override
    public MypageReviewDTO getDetail(int id) {
        return mapper.getDetail(id);
    }

    // 게시글 작성
    @Override
    public void insert(MypageReviewDTO dto) {
        mapper.insert(dto);
    }

    // 게시글 답글 조회
    @Override
    public ReplyDTO getReply(int id) {
        return mapper.getReply(id);
    }

    // 게시글 답글 추가
    @Override
    public void insertReply(ReplyDTO dto) {
        mapper.insertReply(dto);
    }

    // 게시글 상태 변경
    @Override
    public void updateStatus(int id) {
        mapper.updateStatus(id);
    }

    // 백오피스 게시글 검색 결과 개수
    @Override
    public int searchMypageReviewCount(SearchMypageReviewCriteria criteria) {
        return mapper.searchMypageReviewCount(criteria);
    }
    // 백오피스 게시글 검색
    @Override
    public List<MypageReviewDTO> searchMypageReview(SearchMypageReviewCriteria criteria) {
        return mapper.searchMypageReview(criteria);
    }
}
