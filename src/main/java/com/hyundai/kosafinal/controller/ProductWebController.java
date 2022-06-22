package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.service.ProductDetailService;
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

  public ProductWebController(ProductDetailService productDetailService) {
    this.productDetailService = productDetailService;
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

    return "productDetail/detail";
  }

  @RequestMapping("/product/review/page")
  public String getReviewPage(){
    return "productReview/review";
  }
}
