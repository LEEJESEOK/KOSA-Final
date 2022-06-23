package com.hyundai.kosafinal.mapper.product;

import com.hyundai.kosafinal.domain.ProductReviewDTO;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ProductReviewMapperTest {

  @Autowired
  private ProductReviewMapper productReviewMapper;

  @Test
  void getProductInfoByIdTests() {
    List<ProductReviewDTO> list = productReviewMapper.getProductReviewByProductId("MM2B3WJS085M");
    list.forEach(i -> log.info(i.toString()));
  }

  @Test
  void saveProductReviewTest() {
    String str = ""; //이미지가 없을 경우 빈문자열의 바이트를 넣어준다.

    ProductReviewDTO productReviewDTO = ProductReviewDTO.builder()
      .email("test아이디")
      .productId("MM2B3WJS085M")
      .productSize("42")
      .productColorId("DN")
      .title("타이틀 이름")
      .content("테스트 내용 작성")
      .editDate(Date.valueOf(LocalDate.now()))
      .rate(5)
      .imgFile(str.getBytes())
      .ctryLarge("MEN")
      .ctryMedium("SPECIAL SHOP")
      .ctrySmall("CLUB MONACO : 폴로 한잔의 여유")
      .build();

    productReviewMapper.insertProductReview(productReviewDTO);
  }
}