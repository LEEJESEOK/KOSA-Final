package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.service.ProductReviewService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Slf4j
@RestController
public class ProductReviewController {

  private ProductReviewService productReviewService;

  public ProductReviewController(ProductReviewService productReviewService) {
    this.productReviewService = productReviewService;
  }

  @GetMapping("/product_review/{productId}")
  @ResponseBody
  public List<ProductReviewDTO> getProductReviewByProductId(@PathVariable("productId") String productId) {
    List<ProductReviewDTO> list = productReviewService.getProductReviewByProductId(productId);
    for (ProductReviewDTO p : list) {
      String s1 = p.getEmail().substring(0,3);
      String s2 = p.getEmail().substring(3).replaceAll("[a-z/A-Z/0-9]","*");
      p.setEmail(s1+s2);
      if (p.getImgURI() == null || p.getImgURI().equals("")) {
        continue;
      }
      p.setImgURI("https://kosa-aws-bucket.s3.ap-northeast-2.amazonaws.com/" + p.getImgURI());
    }
    return list;
  }

  @GetMapping("/product_review/{productId}/image")
  @ResponseBody
  public List<ProductReviewDTO> getProductReviewByProductIdAndImage(@PathVariable("productId") String productId) {
    List<ProductReviewDTO> list = productReviewService.getProductReviewByProductId(productId);
    List<ProductReviewDTO> imageReviewList = new ArrayList<>();
    for (ProductReviewDTO p : list) {
      String s1 = p.getEmail().substring(0,3);
      String s2 = p.getEmail().substring(3).replaceAll("[a-z/A-Z/0-9]","*");
      p.setEmail(s1+s2);

      if (p.getImgURI() == null || p.getImgURI().equals("")) {
        continue;
      }
      p.setImgURI("https://kosa-aws-bucket.s3.ap-northeast-2.amazonaws.com/" + p.getImgURI());
      imageReviewList.add(p);
    }
    return imageReviewList;
  }

  @GetMapping("/product_review/{productId}/text")
  @ResponseBody
  public List<ProductReviewDTO> getProductReviewByProductIdAndText(@PathVariable("productId") String productId) {
    List<ProductReviewDTO> list = productReviewService.getProductReviewByProductId(productId);
    List<ProductReviewDTO> textReviewList = new ArrayList<>();
    for (ProductReviewDTO p : list) {
      String s1 = p.getEmail().substring(0,3);
      String s2 = p.getEmail().substring(3).replaceAll("[a-z/A-Z/0-9]","*");
      p.setEmail(s1+s2);
      if (p.getImgURI() == null || p.getImgURI().equals("")) {
        textReviewList.add(p);
        System.out.println(p.toString());
      }
    }
    return textReviewList;
  }

  @PostMapping("/product_review")
  public ProductReviewDTO saveProductReview(
    @RequestBody ProductReviewDTO productReviewDTO,
    @RequestPart(required = false) MultipartFile imgFile
  ) {
    log.info("리뷰 입력 창 ==========================");
    log.info("리뷰 입력 정보 : " + productReviewDTO.toString());
    log.info("이미지 파일 정보 : " + imgFile.getOriginalFilename());
    productReviewService.saveProductReview(productReviewDTO, imgFile);
    return productReviewDTO;
  }

  @PostMapping("/product_review/insert")
  public ProductReviewDTO sProductReview(
    MultipartHttpServletRequest request
  ) {
    log.info("리뷰 입력 창 ==========================");
    log.info("리뷰 입력 정보 : " + request.toString());

    ProductReviewDTO productReviewDTO = ProductReviewDTO.builder()
      .email(request.getParameter("email"))
      .productId(request.getParameter("productId"))
      .content(request.getParameter("content"))
      .rate(Integer.parseInt(request.getParameter("rate")))
      .age(request.getParameter("age"))
      .height(request.getParameter("height"))
      .bodyType(request.getParameter("bodyType"))
      .editDate(Date.valueOf(LocalDate.now()))
      .productSize(request.getParameter("productSize"))
      .productColorId(request.getParameter("productColorId"))
      .ctryLarge(request.getParameter("ctryLarge"))
      .ctryMedium(request.getParameter("ctryMedium"))
      .ctrySmall(request.getParameter("ctrySmall"))
      .title(request.getParameter("title"))
      .avgSize(request.getParameter("avgSize"))
      .build();

    List<MultipartFile> fileList = new ArrayList<MultipartFile>();

    // input file 에 아무것도 없을 경우 (파일을 업로드 하지 않았을 때 처리)
    if(!request.getFiles("imgFIle").isEmpty() && request.getFiles("imgFIle").get(0).getSize() != 0){
      fileList = request.getFiles("imgFIle");
      log.info("이미지 파일 정보 : " + fileList.get(0).getOriginalFilename());
      productReviewService.saveProductReview(productReviewDTO, fileList.get(0));
      return productReviewDTO;
    }

    productReviewService.saveProductReview(productReviewDTO, null);
    return productReviewDTO;
  }
}
