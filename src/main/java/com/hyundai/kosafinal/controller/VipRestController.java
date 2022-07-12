package com.hyundai.kosafinal.controller;


import com.hyundai.kosafinal.domain.AuthMemberDTO;
import com.hyundai.kosafinal.domain.LimitedProductDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.domain.SaleDTO;
import com.hyundai.kosafinal.mapper.product.ProductMapper;
import com.hyundai.kosafinal.mapper.userorder.MemberMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/vip")
public class VipRestController {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ProductMapper productMapper;

    // 타임세일 상품 가져오기
    @RequestMapping(value = "/sale" , method = RequestMethod.POST)
    public Map<String, Object> getSaleProduct(@AuthenticationPrincipal AuthMemberDTO authentication) {
        String user_email = authentication.getEmail();
        int grade = memberMapper.findByEmail(user_email).getGradeId();

        if(grade != 4 && grade != 5) { // STAR, THE STAR가 아니면 null 반환
            return null;
        }

        List<SaleDTO> saleDTO = productMapper.selectSaleProduct();
        log.info("타임세일 상품 : " + saleDTO);

        if(saleDTO.size() == 0) { // 타임세일 상품이 없으면 null 반환
            return null;
        } else {
            String pid  = saleDTO.get(0).getPid();
            List<ProductDTO> productDTO = productMapper.selectProductDetailById(pid);

            // 해당 상품 컬러 가져오기
            List<ProductDTO> colorList = productMapper.selectColors(pid);

            Map<String, Object> map = new HashMap<>();
            map.put("sale", saleDTO.get(0));
            map.put("product", productDTO.get(0));
            map.put("colors", colorList);

            return map;
        }
    }

    // 한정상품 가져오기
    @RequestMapping(value = "/limited" , method = RequestMethod.POST)
    public List<LimitedProductDTO> getLimitedProduct(@AuthenticationPrincipal AuthMemberDTO authentication) {
        String user_email = authentication.getEmail();
        int grade = memberMapper.findByEmail(user_email).getGradeId();

        if(grade != 4 && grade != 5) { // STAR, THE STAR가 아니면 null 반환
            return null;
        }

        List<LimitedProductDTO> list = productMapper.selectLimitedProduct();

        log.info("한정상품 : " + list);

        return list;
    }

}
