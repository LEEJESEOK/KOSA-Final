package com.hyundai.kosafinal.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyDTO {
    private int id;
    private String title;
    private String content;
    private Date editDate;
    private String userEmail;

}
