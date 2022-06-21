package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.service.ProductDetailService;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductDetailController {

  private ProductDetailService productDetailService;

  public ProductDetailController(ProductDetailService productDetailService) {
    this.productDetailService = productDetailService;
  }

  @GetMapping("product/detail/{productId}")
  @ResponseBody
  public List<ProductDTO> getProductByProductId(@PathVariable("productId")String productId){
    return productDetailService.getProductDetailList(productId);
  }
}
