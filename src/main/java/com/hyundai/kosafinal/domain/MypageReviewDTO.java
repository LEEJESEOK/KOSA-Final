package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MypageReviewDTO {
    private int id;
    private String title;
    private String content;
    private String category;
    private String userEmail;
    private int status;
    private Date editDate;

}
