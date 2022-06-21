package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.mapper.product.ProductMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

  private ProductMapper productMapper;

  public ProductDetailServiceImpl(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  @Override
  public List<ProductDTO> getProductDetailList(String productId) {
    return productMapper.selectProductDetailById(productId);
  }
}
