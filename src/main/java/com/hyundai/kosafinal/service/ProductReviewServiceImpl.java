package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.domain.SelectProductReviewCriteria;
import com.hyundai.kosafinal.mapper.product.ProductReviewMapper;
import com.hyundai.kosafinal.util.s3.S3Client;
import com.hyundai.kosafinal.util.s3.config.S3.Bucket;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

  private ProductReviewMapper productReviewMapper;

  private S3Client s3Client;

  public ProductReviewServiceImpl(
    ProductReviewMapper productReviewMapper,
    S3Client s3Client
  ) {
    this.productReviewMapper = productReviewMapper;
    this.s3Client = s3Client;
  }

  @Override
  public List<ProductReviewDTO> getProductReviewByProductId(SelectProductReviewCriteria selectProductReviewCriteria) {
    return productReviewMapper.getProductReviewByProductId(selectProductReviewCriteria);
  }

  @Override
  public List<ProductReviewDTO> getProductReviewAllList(String productId) {
    return productReviewMapper.getProductReviewAll(productId);
  }

  @Override
  public void saveProductReview(ProductReviewDTO productReviewDTO, MultipartFile imgFile) {
    if(imgFile != null){
      this.uploadProfileImage(imgFile, productReviewDTO);
    }
    productReviewMapper.insertProductReview(productReviewDTO);
  }

  //상품 리뷰 갯수
  @Override
  public int getProductReviewCountById(SelectProductReviewCriteria selectProductReviewCriteria) {
    return productReviewMapper.getProductReviewCount(selectProductReviewCriteria);
  }

  @Override
  public int getProductReviewCountByIdAndImg(SelectProductReviewCriteria selectProductReviewCriteria) {
    return productReviewMapper.getProductReviewCountByImg(selectProductReviewCriteria);
  }

  @Override
  public int getProductReviewCountByIdAndText(SelectProductReviewCriteria selectProductReviewCriteria) {
    return productReviewMapper.getProductReviewCountByText(selectProductReviewCriteria);
  }

  // 업로드 이미지 -> 키 값을 가져오기
  public String uploadProfileImage(
    MultipartFile image,
    ProductReviewDTO productReviewDTO
  ) {
    if (image.isEmpty()) {
      return null;
    }

    String key =
      "members/" + productReviewDTO.getEmail()
        + "/product/"
        + productReviewDTO.getProductId()
        + "/"
        + UUID.randomUUID()
        + this.s3Client.getExtension(image);

    String profileKey = this.s3Client.upload(
      Bucket.IMAGE,
      image,
      key
    );
    productReviewDTO.setImgURI(profileKey);

    return key;
  }



}
