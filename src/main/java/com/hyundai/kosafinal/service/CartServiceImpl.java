package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.CartDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.entity.CartProduct;
import com.hyundai.kosafinal.mapper.product.ProductMapper;
import com.hyundai.kosafinal.mapper.userorder.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper mapper;

    @Autowired
    private ProductMapper pMapper;

    // 장바구니 목록 조회
    @Override
    public List<CartProduct> getList(String userEmail) {
        return mapper.getList(userEmail);
    }

    // 장바구니 추가
    @Override
    public void insert(CartDTO dto) {
        mapper.insert(dto);
    }

    // 장바구니 수정
    @Override
    public void update(CartDTO dto) {
        mapper.update(dto);
    }

    // 장바구니 삭제
    @Override
    public void delete(int id, String userEmail) {
        mapper.delete(id, userEmail);
    }

    @Override
    public List<ProductDTO> selectColors(String productId) {
        return pMapper.selectColors(productId);
    }

    @Override
    public List<ProductDTO> selectSize(String productId) {
        return pMapper.selectSize(productId);
    }

}
