package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.MypageReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageReviewMapper {

    // 게시글 목록 조회
    public List<MypageReviewDTO> getList(String userEmail);

    // 게시글 조회
    public MypageReviewDTO getDetail(int id);

    // 게시글 작성
    public void insert(MypageReviewDTO dto);

}
