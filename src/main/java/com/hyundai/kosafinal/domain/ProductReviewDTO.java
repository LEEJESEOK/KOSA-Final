package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private String avgSize;
    private double sentiment_percent;
    private String sentiment_type;
}
