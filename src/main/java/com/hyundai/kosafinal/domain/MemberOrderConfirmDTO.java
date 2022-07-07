package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderConfirmDTO {
  private int confirm;
  private String pSize;
  private String pColor;
}
