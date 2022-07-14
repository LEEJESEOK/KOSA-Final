package com.hyundai.kosafinal.mapper.product;

import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.domain.SelectProductReviewCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductReviewMapper {

  List<ProductReviewDTO> getProductReviewByProductId(SelectProductReviewCriteria selectProductReviewCriteria);

  List<ProductReviewDTO> getProductReviewByProductIdAndImg(SelectProductReviewCriteria selectProductReviewCriteria);

  List<ProductReviewDTO> getProductReviewByProductIdAndText(SelectProductReviewCriteria selectProductReviewCriteria);

  List<ProductReviewDTO> getProductReviewAll(@Param("productId") String productId);

  void insertProductReview(ProductReviewDTO productReviewDTO);

  int getProductReviewCount(SelectProductReviewCriteria selectProductReviewCriteria);

  int getProductReviewCountByText(SelectProductReviewCriteria selectProductReviewCriteria);

  int getProductReviewCountByImg(SelectProductReviewCriteria selectProductReviewCriteria);

  List<ProductReviewDTO> selectProductReviewByEmail(String email);

}
