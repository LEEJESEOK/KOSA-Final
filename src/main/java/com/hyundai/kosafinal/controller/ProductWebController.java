package com.hyundai.kosafinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductWebController {

  @RequestMapping("/product/detail")
  public String getSignupPage() {
    return "productDetail/detail";
  }

  @RequestMapping("/product/review/page")
  public String getReviewPage(){
    return "productReview/review";
  }
}
