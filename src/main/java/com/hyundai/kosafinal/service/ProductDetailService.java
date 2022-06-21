package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.ProductDTO;
import java.util.List;

public interface ProductDetailService {

  List<ProductDTO> getProductDetailList(String productId);

}
