package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.AuthMemberDTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.entity.CartProduct;
import com.hyundai.kosafinal.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/cart")
@PreAuthorize("hasRole('ROLE_USER')")
public class CartController {

    @Autowired
    private CartService service;

    // 장바구니 페이지 이동
    @GetMapping("/cartList")
    public void goCartList(Model model, @AuthenticationPrincipal AuthMemberDTO authentication) {
        String userEmail = authentication.getEmail();
        List<CartProduct> list = service.getList(userEmail);
        log.info("Cart: " + list);

        for (CartProduct cp : list) {
            cp.setColors(service.selectColors(cp.getPid()));
            cp.setSizes(service.selectSize(cp.getPid()));
        }
        model.addAttribute("cartList", list);
    }
}
