package com.hyundai.kosafinal.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ProductReviewDTO {
    private int id;
    private Date editDate;
    private int rate;
    private String title;
    private String content;
    private String productSize;
    private String imageUri;
    private String productColorId;
    private String productId;
    private String email;

}
