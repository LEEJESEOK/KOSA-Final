package com.hyundai.kosafinal.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MypageReviewDTO {
    private int id;
    private String title;
    private String content;
    private Date editDate;
    private int status;
    private String category1;
    private String category2;
    private String userEmail;
    private int replyId;

}
