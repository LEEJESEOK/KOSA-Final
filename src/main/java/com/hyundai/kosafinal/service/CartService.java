package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.CartDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.entity.CartProduct;

import java.util.List;

public interface CartService {

    // 장바구니 목록 조회
    public List<CartProduct> getList(String userEmail);

    // 장바구니 추가
    public void insert(CartDTO dto);

    // 장바구니 수정
    public void update(CartDTO dto);

    // 장바구니 삭제
    public void delete(int id, String userEmail);

    // 상품별 색상 조회
    public List<ProductDTO> selectColors(String productId);

    // 상품별 사이즈 조회
    public List<ProductDTO> selectSize(String productId);

}
