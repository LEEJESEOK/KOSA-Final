package com.hyundai.kosafinal.service;

import static org.junit.jupiter.api.Assertions.*;

import com.amazonaws.util.IOUtils;
import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.util.s3.S3Client;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import javax.sql.rowset.serial.SerialBlob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Slf4j
@SpringBootTest
class ProductReviewServiceImplTest {

  @Autowired
  ProductReviewService productReviewService;

  @Autowired
  S3Client s3Client;

  @Test
  void saveProductReview() throws IOException {

    File file = new File("/Users/yujihun/Downloads/testImg.jpeg");

    FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
    try {
      InputStream input = new FileInputStream(file);
      OutputStream os = fileItem.getOutputStream();
      IOUtils.copy(input, os);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

    log.info(Files.probeContentType(file.toPath()));
    log.info(multipartFile.toString());
    log.info(multipartFile.isEmpty() ? "비어있다." : "존재한다.");

    ProductReviewDTO productReviewDTO = ProductReviewDTO.builder()
      .email("dbwlgna677798")
      .productId("MM2B3WJS085M")
      .productSize("42")
      .productColorId("DN")
      .title("제발 성공해줘요,.,,,,")
      .content("드가자 드가자 드가자 드가자 ")
      .editDate(Date.valueOf(LocalDate.now()))
      .rate(4)
      .imgURI("")
      .ctryLarge("MEN")
      .ctryMedium("SPECIAL SHOP")
      .ctrySmall("CLUB MONACO : 폴로 한잔의 여유")
      .build();

    productReviewService.saveProductReview(productReviewDTO, multipartFile);
  }

  @Test
  void uploadProfileImage() {
  }
}