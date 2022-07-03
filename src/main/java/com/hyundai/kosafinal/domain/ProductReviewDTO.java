package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

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
    private String ctryLarge;
    private String ctryMedium;
    private String ctrySmall;
    private String age;
    private String height;
    private String bodyType;
}
