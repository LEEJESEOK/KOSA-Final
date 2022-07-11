package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleDTO {
    private int id;
    private String pid;
    private String pcolor;
    private String psize;
    private String categoryLarge;
    private String categoryMedium;
    private String categorySmall;
    private int originPrice;
    private int salePrice;
    private Date startDate;
    private Date endDate;
}
