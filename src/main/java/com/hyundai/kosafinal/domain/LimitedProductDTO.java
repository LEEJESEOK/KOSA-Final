package com.hyundai.kosafinal.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LimitedProductDTO {
    private String id;
    private String size;
    private String colorId;
    private String name;
    private String brand;
    private String categoryLarge;
    private String categoryMedium;
    private String categorySmall;
    private String detail;
    private int price;
    private String image1Uri;
    private String image2Uri;
    private String image3Uri;
    private String colorChipUri;
    private String stockAmount;
}
