package com.hyundai.kosafinal.domain;

import lombok.Data;
import java.util.Date;

@Data
public class MemberDTO {
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
}
