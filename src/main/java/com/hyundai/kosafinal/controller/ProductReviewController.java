package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.service.ProductReviewService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProductReviewController {

  private ProductReviewService productReviewService;

  public ProductReviewController(ProductReviewService productReviewService) {
    this.productReviewService = productReviewService;
  }

  @GetMapping("/product_review/{productId}")
  @ResponseBody
  public List<ProductReviewDTO> getProductReviewByProductId(@PathVariable("productId")String productId){
    return productReviewService.getProductReviewByProductId(productId);
  }

  @PostMapping("/product_review")
  public ProductReviewDTO saveProductReview(@RequestBody ProductReviewDTO productReviewDTO,
    @RequestPart(required = false) MultipartFile imgFile
    ){
    productReviewService.saveProductReview(productReviewDTO, imgFile);
    return productReviewDTO;
  }
}
