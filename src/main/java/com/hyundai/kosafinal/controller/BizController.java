package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.CategoryDTO;
import com.hyundai.kosafinal.service.ProductService;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/biz")
public class BizController {

    private ProductService productService;

    public BizController(ProductService productService) {
        this.productService = productService;
    }

    static List<String> toBreadCrumbs(String[] routes) {
        List<String> breadCrumbs = new ArrayList<>();
        for (int i = 0; i < routes.length; ++i)
            breadCrumbs.add(routes[i]);

        return breadCrumbs;
    }

    /**
     * 대시보드, 기본 페이지<br>
     *
     * @param model
     */
    @RequestMapping(value = {"", "/", "/dashboard"})
    public String index(Model model) {
        List<String> breadCrumbs = toBreadCrumbs(new String[]{
                "Dashboard"
        });
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("pageTitle", breadCrumbs.get(breadCrumbs.size() - 1));

        return "biz/index";
    }

    /**
     * 상품관리 > 상품 등록<br>
     *
     * @param model
     */
    @RequestMapping("/product/productRegister")
    public String productRegister(Model model) {
        List<String> breadCrumbs = toBreadCrumbs(new String[]{
                "상품관리", "상품 등록"
        });
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("pageTitle", breadCrumbs.get(breadCrumbs.size() - 1));

        //대분류, 중분류, 소분류 뿌리기
        List<CategoryDTO> ctyList = productService.getCategoryList();

        List<String> largeCtry = new ArrayList<>();
        List<String> mediumCtry = new ArrayList<>();
        List<String> smallCtry = new ArrayList<>();

        ctyList.stream().forEach(c -> {
            if (!largeCtry.contains(c.getLarge())) {
                largeCtry.add(c.getLarge());
            }
            if (!mediumCtry.contains(c.getMedium())) {
                mediumCtry.add(c.getMedium());
            }
            if (!smallCtry.contains(c.getSmall())) {
                smallCtry.add(c.getSmall());
            }
        });

        model.addAttribute("largeCtry", largeCtry);
        model.addAttribute("mediumCtry", mediumCtry);
        model.addAttribute("smallCtry", smallCtry);

        return "biz/product/productRegister";
    }

    /**
     * 상품관리 > 상품 목록<br>
     *
     * @param model
     */
    @RequestMapping("/product/productManage")
    public String productManage(Model model) {
        List<String> breadCrumbs = toBreadCrumbs(new String[]{
                "상품관리", "상품 목록"
        });
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("pageTitle", breadCrumbs.get(breadCrumbs.size() - 1));

        return "biz/product/productManage";
    }

    /**
     * 상품관리 > 상품 분류 관리<br>
     *
     * @param model
     */
    @RequestMapping("/product/categoryManage")
    public String categoryManage(Model model) {
        List<String> breadCrumbs = toBreadCrumbs(new String[]{
                "상품관리", "상품 분류 관리"
        });
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("pageTitle", breadCrumbs.get(breadCrumbs.size() - 1));

        return "biz/product/categoryManage";
    }

    /**
     * 주문관리 > 주문목록<br>
     *
     * @param model
     */
    @RequestMapping("/order/orderList")
    public void orderList(Model model) {
        List<String> breadCrumbs = toBreadCrumbs(new String[]{
                "주문관리", "주문목록"
        });
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("pageTitle", breadCrumbs.get(breadCrumbs.size() - 1));

    }

    /**
     * 주문관리 > 배송관리<br>
     *
     * @param model
     */
    @RequestMapping("/order/shipping")
    public void shipping(Model model) {
        List<String> breadCrumbs = toBreadCrumbs(new String[]{
                "주문관리", "배송관리"
        });
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("pageTitle", breadCrumbs.get(breadCrumbs.size() - 1));

    }

    /**
     * 주문관리 > 취소/교환/반품/환불<br>
     *
     * @param model
     */
    @RequestMapping("/order/orderModify")
    public void orderModify(Model model) {
        List<String> breadCrumbs = toBreadCrumbs(new String[]{
                "주문관리", "취소/교환/반품/환불"
        });
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("pageTitle", breadCrumbs.get(breadCrumbs.size() - 1));

    }

    /**
     * 고객관리 > 고객목록<br>
     *
     * @param model
     * @return
     */
    @RequestMapping("/member/memberManage")
    public String memberManage(Model model) {
        List<String> breadCrumbs = toBreadCrumbs(new String[]{
                "고객관리", "고객목록"
        });
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("pageTitle", breadCrumbs.get(breadCrumbs.size() - 1));

        return "biz/member/memberManage";
    }

    /**
     * @param model
     * @return
     */
    @RequestMapping("/member/reviewManage")
    public String reviewManage(Model model) {
        List<String> breadCrumbs = toBreadCrumbs(new String[]{
                "고객관리", "문의목록"
        });
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("pageTitle", breadCrumbs.get(breadCrumbs.size() - 1));

        return "biz/member/reviewManage";
    }

    /**
     * VIP > VIP 목록
     *
     * @param model
     * @return
     */
    @RequestMapping("/vip/vipManage")
    public String vipManage(Model model) {
        List<String> breadCrumbs = toBreadCrumbs(new String[]{
                "VIP", "VIP 목록"
        });
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("pageTitle", breadCrumbs.get(breadCrumbs.size() - 1));

        return "biz/vip/vipManage";
    }

    @RequestMapping(value = "/vip/{id}/detail")
    public String vipDetail(Model model, @PathVariable("id") String id) {
        List<String> breadCrumbs = toBreadCrumbs(new String[]{
                "VIP", "VIP 정보"
        });
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("pageTitle", breadCrumbs.get(breadCrumbs.size() - 1));

        model.addAttribute("id", id);

        return "biz/vip/vipDetail";
    }
}
