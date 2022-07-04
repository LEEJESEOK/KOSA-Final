package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.AuthMemberDTO;
import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.OrderItemDTO;
import com.hyundai.kosafinal.domain.OrderedListDTO;
import com.hyundai.kosafinal.entity.CartProduct;
import com.hyundai.kosafinal.entity.OrderProduct;
import com.hyundai.kosafinal.service.MemberService;
import com.hyundai.kosafinal.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/order")
@PreAuthorize("hasRole('USER')")
public class OrderController {

    @Autowired
    OrderService oService;
    @Autowired
    MemberService mService;

    // 상품 주문
    // POST 방식의 폼 데이터로 주문 진행
    // Parameter: 주문할 상품 개수(pcount), Map타입의 주문할 상품 정보(orders)
    @PostMapping("/orderForm")
    public void orderForm(@RequestParam Map<String, String> orders, Model model,
                          @AuthenticationPrincipal AuthMemberDTO authentication) {
        // 회원 정보 찾기
        String userEmail = authentication.getEmail();
        Member2DTO member = mService.findByEmail(userEmail);

        // 주문 상품 리스트
        List<CartProduct> orderList = new ArrayList<CartProduct>();

        // 주문할 상품 갯수만큼 반복
        int cnt = Integer.parseInt(orders.get("pcount"));
        int totalPrice = 0;
        for (int i = 1; i <= cnt; i++) {
            CartProduct item = new CartProduct();
            item.setPid(orders.get("pid" + i));
            item.setName(orders.get("name" + i));
            item.setBrand(orders.get("brand" + i));
            item.setImage(orders.get("image" + i));
            item.setPcolor(orders.get("pcolor" + i));
            item.setPsize(orders.get("psize" + i));
            item.setAmount(Integer.parseInt(orders.get("amount" + i)));
            int price = Integer.parseInt(orders.get("price" + i));
            item.setPrice(price);
            totalPrice += price;

            orderList.add(item);
        }

        log.info("Items: " + orderList);

        // 회원 정보
        model.addAttribute("member", member);
        // 주문 상품
        model.addAttribute("orderList", orderList);
        // 주문 총 금액
        model.addAttribute("totalPrice", totalPrice);

        log.info("MEMBER: " + member);
    }

    @GetMapping("/orderComplete")
    public void orderComplete(@RequestParam String id, Model model) {
        OrderedListDTO order = oService.getOrderDetail(id);
        List<OrderProduct> orderItems = oService.getOrderItem(id);
        log.info("ORDER COMPLETE: " + order);

        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItems);
    }
    

}
