package com.hyundai.kosafinal.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@ToString
public class AuthMemberDTO extends User {

    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private String name;
    private int login_type; //from_social
    Map<String,Object> OA2Attributes;
    // 구성자 설정
    public AuthMemberDTO(String email,String password,String name,int login_type,
                         List<GrantedAuthority> authorities) {
        // password는 부모클래스 사용
        super(email, password,authorities);
        this.email=email;
        this.login_type=login_type;
        this.name=name;
    }// end AuthMemberDTO

}// end class
