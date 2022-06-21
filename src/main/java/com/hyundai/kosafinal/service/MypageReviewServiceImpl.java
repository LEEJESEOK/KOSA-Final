package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.MypageReviewDTO;
import com.hyundai.kosafinal.domain.ReplyDTO;
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
    public ReplyDTO getReply(int id) {
        return mapper.getReply(id);
    }
}
