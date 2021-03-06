package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member2DTO {
    private String email;
    private String password;
    private String name;
    private int gender;
    private Date birth;
    private String tel;
    private String address1;
    private String address2;
    private String zipcode;
    private int height;
    private int weight;
    private int point;
    private Date recentLoginDate;
    private Date signUpDate;
    private int status;
    private int gradeId;
    private int login_type;
    private String role_set;

}
