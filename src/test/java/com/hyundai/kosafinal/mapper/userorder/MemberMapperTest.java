package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.GradeDTO;
import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.RoleSetDTO;
import com.hyundai.kosafinal.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemberMapperTest {
    @Autowired
    private MemberMapper mapper;
    @Autowired
    private PasswordEncoder passwordencoder;


    @Test
    void insertMember() {
        MemberDTO member = new MemberDTO();

        member.setEmail("TEST3");
        member.setPassword(passwordencoder.encode("1111"));
        member.setName("이승연");
        member.setGender(0);
        Date date=new Date();
        member.setBirth(date);
        member.setTel("01088888888");
        member.setAddress1("동작구");
        member.setAddress2("보라매로");
        member.setZipcode("14122");
        member.setHeight(178);
        member.setWeight(80);
        member.setPoint(111111);
        member.setRecentLoginDate(date);
        member.setSignUpDate(date);
        member.setStatus(0);
        member.setGradeId(1);
        member.setLogin_type(0);

        mapper.insertMember(member);

        }



    @Test
    void insertRoleSet() {
            RoleSetDTO roleset = new RoleSetDTO();
            String role = null;
            roleset.setUser_email("TEST1");
            roleset.setRole_set(MemberRole.USER.name());
            mapper.insertRoleSet(roleset);
        }

    @Test
    void loginMember() {
    }

    @Test
    void checkId() {
        MemberDTO MemberDTO = new MemberDTO();
        int result=mapper.checkId("TEST1");
        System.out.println(result);
    }

    @Test
    void updateMember() {
        MemberDTO member = new MemberDTO();
        member.setEmail("TEST1");
        member.setPassword(passwordencoder.encode("1121"));
        member.setName("이제석");
        member.setGender(0);
        Date date=new Date();
        member.setBirth(date);
        member.setTel("01058585858");
        member.setAddress1("인천");
        member.setAddress2("부평");
        member.setHeight(177);
        member.setWeight(88);

        mapper.updateMember(member);
    }

    @Test
    void deleteMember() {
        String email="test2";
        mapper.deleteMember(email);
    }

    @Test
    void findByEmail(){
        String email="TEST1";
        int login_type=0;
        Member2DTO result=mapper.findByEmail(email);
        System.out.println(result);
    }
}