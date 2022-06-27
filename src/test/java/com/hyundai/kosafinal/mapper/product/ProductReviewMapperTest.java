package com.hyundai.kosafinal.mapper.product;

import com.hyundai.kosafinal.domain.ProductReviewDTO;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;
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
  void getProductInfoByIdTests() throws IOException {
    List<ProductReviewDTO> list = productReviewMapper.getProductReviewByProductId("MM2B3WJS085M");

    int index=0;
    for(ProductReviewDTO p : list){
      p.setImgURI("https://kosa-aws-bucket.s3.ap-northeast-2.amazonaws.com/"+p.getImgURI());
      System.out.println(p.toString());

    }

    list.forEach(i -> log.info(i.toString()));
  }

  @Test
  void saveProductReviewTest() throws IOException, SQLException {
    String str = ""; //이미지가 없을 경우 빈문자열의 바이트를 넣어준다.

    File imageFile = new File("/Users/yujihun/Downloads/testImg.jpeg");

    String path = imageFile.getPath();

    ByteArrayOutputStream bout =  new ByteArrayOutputStream();
    FileInputStream fin = new FileInputStream(path);

    byte[] buf = new byte[1024];
    int read = 0;
    while((read=fin.read(buf,0,buf.length))!=-1){
      bout.write(buf,0,read);
    }
    fin.close();
    byte[] imageData = bout.toByteArray();
    Blob d = new SerialBlob(imageData);

    ProductReviewDTO productReviewDTO = ProductReviewDTO.builder()
      .email("db3333398")
      .productId("MM2B3WJS085M")
      .productSize("42")
      .productColorId("DN")
      .title("아이유")
      .content("주저리주저리주저리")
      .editDate(Date.valueOf(LocalDate.now()))
      .rate(0)
      .imgURI("")
      .ctryLarge("MEN")
      .ctryMedium("SPECIAL SHOP")
      .ctrySmall("CLUB MONACO : 폴로 한잔의 여유")
      .build();

    productReviewMapper.insertProductReview(productReviewDTO);
  }


}