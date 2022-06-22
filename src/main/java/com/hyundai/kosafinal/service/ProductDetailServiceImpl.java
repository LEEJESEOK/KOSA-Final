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

  @Override
  public List<ProductDTO> getProductByIdAndColor(
    String productId,
    String color
  ) {
    return productMapper.selectProductByIdAndColor(productId, color);
  }

  @Override
  public List<ProductDTO> getColorsChip(String productId) {
    return productMapper.selectColors(productId);
  }

  @Override
  public List<ProductDTO> getProductSizeList(String productId) {
    return productMapper.selectSize(productId);
  }
}
