package com.hyundai.kosafinal.domain;

import java.sql.Blob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
public class ProductReviewDTO {
    private int id;
    private String email;
    private String productId;
    private String productSize;
    private String productColorId;
    private String title;
    private String content;
    private Date editDate;
    private int rate;
    private String imgURI;
//    private String imageUri;
    private String ctryLarge;
    private String ctryMedium;
    private String ctrySmall;
}
