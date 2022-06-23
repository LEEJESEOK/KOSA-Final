package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.entity.CartProduct;
import com.hyundai.kosafinal.service.CartService;
import com.hyundai.kosafinal.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
public class IndexController {

    // 필요한 서비스 자동주입
    @Autowired
    private CartService cService;

    // 1:1 문의 게시판 목록 페이지 이동
    @GetMapping("/mypage/review/list")
    public String goMypageReviewList() {
        return "mypage/reviewList";
    }

    // 1:1 문의 게시판 작성 페이지 이동
    @GetMapping("/mypage/review/insert")
    public String goMypageReviewForm() {
        return "mypage/reviewInsert";
    }

    // 1:1 문의 게시판 작성 완료 페이지 이동
    @GetMapping("/mypage/review/complete")
    public String goMypageReviewComplete() {
        return "mypage/reviewInsertComplete";
    }


    // 장바구니 페이지 이동
    @GetMapping("/cart/list")
    public String goCartList(Model model) {
        List<CartProduct> list = cService.getList("TEST1");
        log.info("Cart: " + list);

        for(CartProduct cp : list) {
            cp.setColors(cService.selectColors(cp.getPid()));
            cp.setSizes(cService.selectSize(cp.getPid()));
        }
        model.addAttribute("cartList", list);

        return "cart/cartList";
    }
}
