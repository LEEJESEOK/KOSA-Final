package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.ProductReviewDTO;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ProductReviewService {

   List<ProductReviewDTO> getProductReviewByProductId(String id);

   void saveProductReview(ProductReviewDTO productReviewDTO, MultipartFile imgFile);
}
