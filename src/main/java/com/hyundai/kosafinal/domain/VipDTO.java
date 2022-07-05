package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VipDTO {
    private String email;
    private String password;
    private String name;
    private Date recentLoginDate;
    private Date signUpDate;
    private int status;
    private int gradeId;
    private int period;
    private int cnt_1 ;
    private int flag;

}
