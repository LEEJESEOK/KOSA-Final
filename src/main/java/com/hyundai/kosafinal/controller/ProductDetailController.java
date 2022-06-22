package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.service.ProductDetailService;
import java.util.Collections;
import java.util.List;
import lombok.Builder.Default;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProductDetailController {

  private ProductDetailService productDetailService;

  public ProductDetailController(ProductDetailService productDetailService) {
    this.productDetailService = productDetailService;
  }

  @GetMapping("/product/{productId}")
  @ResponseBody
  public ProductDTO getProductByProductId(@PathVariable("productId")String productId,
    @RequestParam(value="color", required = false, defaultValue = "") String color ){

    List<ProductDTO> productDTOS = productDetailService.getProductByIdAndColor(productId, color);
    log.info(productDTOS.get(0).toString());
    return productDTOS.get(0);
  }
}
