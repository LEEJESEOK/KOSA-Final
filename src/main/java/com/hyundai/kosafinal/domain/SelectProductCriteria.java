package com.hyundai.kosafinal.domain;

import com.hyundai.kosafinal.entity.Criteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SelectProductCriteria extends Criteria {

  private String keyType;
  private String keyContent;
  private String categoryLarge;
  private String categoryMedium;
  private String categorySmall;
  private String brand;
  private String status;

  public SelectProductCriteria(int pageNum, int amount) {
    super(pageNum, amount);
  }

}
