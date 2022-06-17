package com.hyundai.kosafinal.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;


@Getter
@Setter
@ToString
public class AuthMemberDTO extends User {

    private static final long serialVersionUID = 1L;
    private int login_type;
    private String email;
    private String name;

    // 구성자 설정
    public AuthMemberDTO(String email,
                         String password, int login_type,
                         List<GrantedAuthority> authorities) {
        // password는 부모클래스 사용
        super(email, password, authorities);
        this.email = email;
        this.login_type = login_type;
    }// end AuthMemberDTO
}// end class
