package com.hyundai.kosafinal.mapper.product;

import com.hyundai.kosafinal.domain.ProductReviewDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductReviewMapper {

  List<ProductReviewDTO> getProductReviewByProductId(@Param("productId")String productId);

  void insertProductReview(ProductReviewDTO productReviewDTO);
}
