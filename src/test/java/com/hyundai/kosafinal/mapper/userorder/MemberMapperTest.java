package com.hyundai.kosafinal.mapper.userorder;


import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.RoleSetDTO;
import com.hyundai.kosafinal.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootTest
class MemberMapperTest {
    @Autowired
    private MemberMapper mapper;
    @Autowired
    private PasswordEncoder passwordencoder;


    @Test
    void insertMember() {
        MemberDTO member = new MemberDTO();

        member.setEmail("TEST7708");
        member.setPassword(passwordencoder.encode("1111"));
        member.setName("이승연");
        member.setGradeId(2);
//        member.setGender(0);
        Date date = new Date();
//        member.setBirth(date);
//        member.setTel("01088888888");
//        member.setAddress1("동작구");
//        member.setAddress2("보라매로");
//        member.setZipcode("14122");
//        member.setHeight(178);
//        member.setWeight(80);
//        member.setPoint(111111);
//        member.setRecentLoginDate(date);
//        member.setSignUpDate(date);
//        member.setStatus(0);
//        member.setGradeId(1);
//        member.setLogin_type(0);
        System.out.println(member);
        mapper.insertMember(member);

    }


    @Test
    void insertRoleSet() {
        RoleSetDTO roleset = new RoleSetDTO();
        String role = null;
        roleset.setUser_email("TEST7788");
        roleset.setRole_set(MemberRole.USER.toString());
        mapper.insertRoleSet(roleset);
    }

    @Test
    void loginMember() {
    }

    @Test
    void checkId() {
        MemberDTO MemberDTO = new MemberDTO();
        int result = mapper.checkId("TEST1");
        System.out.println(result);
    }

    @Test
    void updateMember() {
        MemberDTO member = new MemberDTO();
        member.setEmail("TEST1");
        member.setPassword(passwordencoder.encode("1121"));
        member.setName("이");
        member.setGender(0);
        Date date = new Date();
        member.setBirth(date);
        member.setTel("01");
        member.setAddress1("인천");
        member.setAddress2("부평");
        member.setHeight(177);
        member.setWeight(88);
        System.out.println(mapper.updateMember(member));

    }

    @Test
    void deleteMember() {
        String email = "TEST588";
        mapper.deleteMember(email);
    }

    @Test
    void findByEmail() {
        String email = "isaa1155";
        int login_type = 0;
        Member2DTO result = mapper.findByEmail(email);
        System.out.println(result);
    }
}