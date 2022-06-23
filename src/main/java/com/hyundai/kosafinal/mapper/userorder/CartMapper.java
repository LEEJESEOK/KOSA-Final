package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.CartDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.domain.ReplyDTO;
import com.hyundai.kosafinal.entity.CartProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {

    // 장바구니 목록 조회
    public List<CartProduct> getList(String userEmail);

    // 장바구니 추가
    public void insert(CartDTO dto);

    // 장바구니 수정
    public void update(CartDTO dto);

    // 장바구니 삭제
    public void delete(int id, String userEmail);

}
