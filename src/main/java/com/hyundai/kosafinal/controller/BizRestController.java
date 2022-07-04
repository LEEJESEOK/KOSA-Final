package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LEE JESEOK
 */
@RestController
@RequestMapping("/api/biz")
public class BizRestController {

    @Autowired
    ProductService productService;

    @PostMapping("/product/register")
    public Map<String, Object> productRegister(@RequestParam ProductDTO productDTO) {
        Map<String, Object> map = new HashMap<>();

        // 상품 이미지 aws 등록
        // 상품 정보 DB 등록

        map.put("result", true);
        return map;
    }
}
