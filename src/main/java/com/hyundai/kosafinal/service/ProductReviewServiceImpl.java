package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.mapper.product.ProductReviewMapper;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.rowset.serial.SerialException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

  private ProductReviewMapper productReviewMapper;

  public ProductReviewServiceImpl(ProductReviewMapper productReviewMapper) {
    this.productReviewMapper = productReviewMapper;
  }

  @Override
  public List<ProductReviewDTO> getProductReviewByProductId(String id) {
    return productReviewMapper.getProductReviewByProductId(id);
  }

  @Override
  public void saveProductReview(ProductReviewDTO productReviewDTO, MultipartFile imgFile) {

    //img 파일 url로 변경후 DTO에 담기

    Map<String, Object> param = new HashMap<String, Object>();


    byte[] bytes;
    productReviewDTO.setImgFile(null);

    if (imgFile != null && imgFile.isEmpty()) {
      String fileName = imgFile.getOriginalFilename();
      try {
        bytes = imgFile.getBytes();
        try {
          Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
          param.put("file", blob);
          param.put("file_name", fileName);
          param.put("file_size", blob.length());

          productReviewDTO.setImgFile(bytes);
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    productReviewMapper.insertProductReview(productReviewDTO);
  }
}
