package com.hyundai.kosafinal.domain;

import com.hyundai.kosafinal.entity.Criteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SelectProductReviewCriteria extends Criteria {

  private String productId;

  public SelectProductReviewCriteria(int pageNum, int amount) {
    super(pageNum, amount);
  }

}
