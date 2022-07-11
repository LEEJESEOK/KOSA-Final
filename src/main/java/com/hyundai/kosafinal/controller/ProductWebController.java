package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.AuthMemberDTO;
import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.service.MemberService;
import com.hyundai.kosafinal.service.ProductDetailService;
import com.hyundai.kosafinal.service.ProductReviewService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductWebController {

  private ProductDetailService productDetailService;

  private ProductReviewService productReviewService;

  private MemberService mService;

  public ProductWebController(
    ProductDetailService productDetailService,
    ProductReviewService productReviewService,
    MemberService mService
  ) {
    this.productDetailService = productDetailService;
    this.productReviewService = productReviewService;
    this.mService = mService;
  }

  @RequestMapping("/product/detail/{productId}")
  public String getSignupPage(Model model, @PathVariable("productId") String productId, Authentication authentication,
                              @RequestParam(value = "status", required = false, defaultValue = "0") String status)
  {
    //컬러칩 ID + URI
    List<ProductDTO> colorList = productDetailService.getColorsChip(productId);
    model.addAttribute("colorChipList", colorList);

    //사이즈 리스트
    List<ProductDTO> sizeList = productDetailService.getProductSizeList(productId);
    model.addAttribute("sizeList", sizeList);

    //기본 디폴트 정보 삽입
    List<ProductDTO> productDTOList = productDetailService.getProductDetailList(productId);
    ProductDTO dto = productDTOList.get(0);
    if(status.equals("1")) {
      dto.setPrice((int)(dto.getPrice() * 0.8));
    }
    model.addAttribute("productDetail", dto);



    //컬러마다 3개의 이미지씩 등록
    List<ProductDTO> colorImageList = new ArrayList<>();
    for(ProductDTO color : colorList){
      for(ProductDTO product : productDTOList){
        if(color.getColorId().equals(product.getColorId())){
          colorImageList.add(product);
          break;
        }
      }
    }
    model.addAttribute("colorImageList", colorImageList);

    //평균 상품평 및 (긍정, 부정 평균) 계산
    List<ProductReviewDTO> reviewDTOList = productReviewService.getProductReviewAllList(productId);
    long total = 0;
    long imgCnt = 0;
    long textCnt = 0;
    long positiveCnt = 0;
    long negativeCnt = 0;
    double negativeTotal = 0;
    double positiveTotal = 0;
    for(ProductReviewDTO p : reviewDTOList){
      if(p.getSentiment_type().equals("부정")) {
        negativeCnt++;
        negativeTotal += p.getSentiment_percent();
      }
      else{
        positiveCnt++;
        positiveTotal += p.getSentiment_percent();
      }

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
    model.addAttribute("positivePercent", Math.round(positiveTotal/positiveCnt));
    model.addAttribute("negativePercent", Math.round(negativeTotal/negativeCnt));

    if(authentication != null){
      Member2DTO member = mService.findByEmail(authentication.getName());
      String userEmail = member.getEmail();
      model.addAttribute("userEmail", userEmail);
    }

    return "productDetail/detail";
  }

  @RequestMapping("/product/review/page")
  public String getReviewPage(){
    return "productReview/review";
  }
}
