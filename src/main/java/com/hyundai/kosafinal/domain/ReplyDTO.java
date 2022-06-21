package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReplyDTO {
    private int id;
    private String content;
    private Date editDate;
    private String userEmail;
    private int originId;
}
