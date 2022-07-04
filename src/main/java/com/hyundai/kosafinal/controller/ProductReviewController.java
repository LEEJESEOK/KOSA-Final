package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.service.ProductReviewService;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class ProductReviewController {

  private ProductReviewService productReviewService;

  public ProductReviewController(ProductReviewService productReviewService) {
    this.productReviewService = productReviewService;
  }

  @GetMapping("/product_review/{productId}")
  @ResponseBody
  public List<ProductReviewDTO> getProductReviewByProductId(@PathVariable("productId")String productId){
    List<ProductReviewDTO> list = productReviewService.getProductReviewByProductId(productId);
    for(ProductReviewDTO p : list){
      if(p.getImgURI() == null || p.getImgURI().equals("")) continue;
      p.setImgURI("https://kosa-aws-bucket.s3.ap-northeast-2.amazonaws.com/"+p.getImgURI());
    }
    return list;
  }

  @GetMapping("/product_review/{productId}/image")
  @ResponseBody
  public List<ProductReviewDTO> getProductReviewByProductIdAndImage(@PathVariable("productId")String productId){
    List<ProductReviewDTO> list = productReviewService.getProductReviewByProductId(productId);
    List<ProductReviewDTO> imageReviewList = new ArrayList<>();
    for(ProductReviewDTO p : list){
      if(p.getImgURI() == null || p.getImgURI().equals("")) continue;
      p.setImgURI("https://kosa-aws-bucket.s3.ap-northeast-2.amazonaws.com/"+p.getImgURI());
      imageReviewList.add(p);
    }
    return imageReviewList;
  }

  @GetMapping("/product_review/{productId}/text")
  @ResponseBody
  public List<ProductReviewDTO> getProductReviewByProductIdAndText(@PathVariable("productId")String productId){
    List<ProductReviewDTO> list = productReviewService.getProductReviewByProductId(productId);
    List<ProductReviewDTO> textReviewList = new ArrayList<>();
    for(ProductReviewDTO p : list){
      if(p.getImgURI() == null || p.getImgURI().equals("")) {
        textReviewList.add(p);
        System.out.println(p.toString());
      }
    }
    return textReviewList;
  }

  @PostMapping("/product_review")
  public ProductReviewDTO saveProductReview(@RequestBody ProductReviewDTO productReviewDTO,
    @RequestPart(required = false) MultipartFile imgFile
    ){
    productReviewService.saveProductReview(productReviewDTO, imgFile);
    return productReviewDTO;
  }
}
