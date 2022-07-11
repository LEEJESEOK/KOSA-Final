package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.domain.SelectProductReviewCriteria;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ProductReviewService {

   List<ProductReviewDTO> getProductReviewByProductId(SelectProductReviewCriteria selectProductReviewCriteria);

   List<ProductReviewDTO> getProductReviewAllList(String productId);

   void saveProductReview(ProductReviewDTO productReviewDTO, MultipartFile imgFile);

   int getProductReviewCountById(SelectProductReviewCriteria selectProductReviewCriteria);

}
