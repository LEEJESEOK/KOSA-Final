package com.hyundai.kosafinal.controller;


import com.hyundai.kosafinal.domain.AuthMemberDTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.OrderItemDTO;
import com.hyundai.kosafinal.domain.OrderedListDTO;
import com.hyundai.kosafinal.entity.Criteria;
import com.hyundai.kosafinal.entity.OrderProduct;
import com.hyundai.kosafinal.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/mypage")
@PreAuthorize("hasRole('USER')")
public class MypageController {

    @Autowired
    OrderService oService;

    @GetMapping("/modify") //회원정보 수정버튼 눌렀을때
    public void modify(MemberDTO member) {
        log.info("회원정보 수정 페이지 이동");
    }

    @GetMapping(value = {"", "/"}) //마이페이지 첫화면
    public String basic() {
        log.info("마이페이지 이동");
        return "mypage/basic";
    }

    // 주문내역 페이지 이동
    @GetMapping("/myorder")
    public void myorder(@AuthenticationPrincipal AuthMemberDTO authentication, Model model) {
        log.info("주문내역 페이지 이동");
        String userEmail = authentication.getEmail();

        List<OrderedListDTO> orderList = oService.getOrderedList(userEmail);

        model.addAttribute("orderList", orderList);
    }

    // 주문 상세보기
    @GetMapping("/myorderDetail")
    public void myOrderDetail(@RequestParam String id, Model model) {
        log.info("주문 상세 페이지 이동 id : " + id);
        OrderedListDTO order = oService.getOrderDetail(id);
        List<OrderProduct> items = oService.getOrderItem(order.getId());

        model.addAttribute("order", order);
        model.addAttribute("items", items);
    }

    // 1:1 문의 게시판 목록 페이지 이동
    @GetMapping("/reviewList")
    public void reviewList() {
        log.info("리뷰 목록 페이지 이동");
    }

    // 1:1 문의 게시판 작성 페이지 이동
    @GetMapping("/reviewInsert")
    public void reviewInsert() {
        log.info("리뷰 작성 페이지 이동");
    }

    // 1:1 문의 게시판 작성 완료 페이지 이동
    @GetMapping("/reviewInsertComplete")
    public void reviewInsertComplete() {
        log.info("리뷰 작성 완료 페이지 이동");
    }

}
