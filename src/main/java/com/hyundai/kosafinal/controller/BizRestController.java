package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.service.MemberService;
import com.hyundai.kosafinal.service.OrderService;
import com.hyundai.kosafinal.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author LEE JESEOK
 * @author YOU JIHOON
 */
@RestController
@RequestMapping("/api/biz")
public class BizRestController {

    @Autowired
    ProductService productService;
    @Autowired
    MemberService memberService;
    @Autowired
    OrderService orderService;

    @PostMapping("/product/register")
    public ResponseEntity<Integer> productRegister(
            MultipartHttpServletRequest request
    ) {
        ProductDTO productDTO = ProductDTO.builder()
                .id(request.getParameter("id"))
                .size(request.getParameter("size"))
                .colorId(request.getParameter("colorId"))
                .name(request.getParameter("name"))
                .brand(request.getParameter("brand"))
                .categoryLarge(request.getParameter("categoryLarge"))
                .categoryMedium(request.getParameter("categoryMedium"))
                .categorySmall(request.getParameter("categorySmall"))
                .detail(request.getParameter("detail"))
                .price(Integer.parseInt(request.getParameter("price")))
                .stockAmount(request.getParameter("stockAmount"))
                .build();

        List<MultipartFile> files = new ArrayList<>();
        if (!request.getFiles("image1Uri").isEmpty() && request.getFiles("image1Uri").get(0).getSize() != 0) {
            files.add(request.getFile("image1Uri"));
        }
        if (!request.getFiles("image2Uri").isEmpty() && request.getFiles("image2Uri").get(0).getSize() != 0) {
            files.add(request.getFile("image2Uri"));
        }
        if (!request.getFiles("image3Uri").isEmpty() && request.getFiles("image3Uri").get(0).getSize() != 0) {
            files.add(request.getFile("image3Uri"));
        }

        String colorChip = productService.getColorChip(productDTO.getColorId());
        productDTO.setColorChipUri(colorChip);

        int result = productService.saveProductInfo(productDTO, files);
        System.out.println("result : " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/vip/{id}/statistics/login")
    public Map<String, Object> getLoginStatistics(@PathVariable("id") String id) {
        Map<String, Object> responseMap = new HashMap<>();

        return responseMap;
    }

    @GetMapping("/vip/{id}/statistics/order/brand")
    public Map<String, Object> getOrderedBrandCount(@PathVariable("id") String id) {
        Map<String, Object> responseMap = new HashMap<>();

        responseMap.put("data", orderService.getOrderedBrandCountByMemberId(id));

        return responseMap;
    }

    @GetMapping("/vip/{id}/statistics/order/category")
    public Map<String, Object> getOrderedCategoryCount(@PathVariable("id") String id) {
        Map<String, Object> responseMap = new HashMap<>();

        responseMap.put("data", orderService.getOrderedCategoryCountByMemberId(id));

        return responseMap;
    }


}
