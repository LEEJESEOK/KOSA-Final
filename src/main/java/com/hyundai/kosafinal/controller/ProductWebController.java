package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.service.ProductDetailService;
import com.hyundai.kosafinal.service.ProductReviewService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductWebController {

  private ProductDetailService productDetailService;

  private ProductReviewService productReviewService;

  public ProductWebController(
    ProductDetailService productDetailService,
    ProductReviewService productReviewService
  ) {
    this.productDetailService = productDetailService;
    this.productReviewService = productReviewService;
  }

  @RequestMapping("/product/detail/{productId}")
  public String getSignupPage(Model model, @PathVariable("productId") String productId)
  {
    //컬러칩 ID + URI
    List<ProductDTO> colorList = productDetailService.getColorsChip(productId);
    model.addAttribute("colorChipList", colorList);

    //사이즈 리스트
    List<ProductDTO> sizeList = productDetailService.getProductSizeList(productId);
    model.addAttribute("sizeList", sizeList);

    //기본 디폴트 정보 삽입
    List<ProductDTO> productDTOList = productDetailService.getProductDetailList(productId);
    model.addAttribute("productDetail", productDTOList.get(0));

    //컬러마다 3개의 이미지씩 등록
    List<ProductDTO> colorImageList = new ArrayList<>();
    for(ProductDTO color : colorList){
      for(ProductDTO product : productDTOList){
        if(color.getColorId().equals(product.getColorId())){
          colorImageList.add(product);
          System.out.println("  색   "+color.getColorId() +"  아이디  "+product.getId());
          break;
        }
      }
    }
    model.addAttribute("colorImageList", colorImageList);

    //평균 상품평 계산
    List<ProductReviewDTO> reviewDTOList = productReviewService.getProductReviewByProductId(productId);
    long total = 0;
    long imgCnt = 0;
    long textCnt = 0;
    for(ProductReviewDTO p : reviewDTOList){
      total += p.getRate();
      if(p.getImgURI() == null || p.getImgURI().equals("")){
        textCnt++;
      }
    }
    imgCnt = reviewDTOList.size() - textCnt;
    double avgTotal = total / (double) reviewDTOList.size();
    avgTotal = Math.round(avgTotal*100)/100.0;
    model.addAttribute("avgTotal", avgTotal);
    model.addAttribute("reviewCnt", reviewDTOList.size());
    model.addAttribute("imgCnt", imgCnt);
    model.addAttribute("textCnt", textCnt);

    return "productDetail/detail";
  }

  @RequestMapping("/product/review/page")
  public String getReviewPage(){
    return "productReview/review";
  }
}
