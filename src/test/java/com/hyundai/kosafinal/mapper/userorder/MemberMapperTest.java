package com.hyundai.kosafinal.mapper.userorder;


import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.RoleSetDTO;
import com.hyundai.kosafinal.entity.MemberRole;
import com.hyundai.kosafinal.entity.SearchMemberCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

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
//
//    @Test
//    void deleteMember() {
//        String email = "TEST1";
//        mapper.deleteMember(email);
//    }
//
//    @Test
//    void findByEmail() {
//        String email = "isaa1155";
//        int login_type = 0;
//        Member2DTO result = mapper.findByEmail(email);
//        System.out.println(result);
//    }

    @Test
    void insertRoleSet() {
        RoleSetDTO roleset = new RoleSetDTO();
        String role = null;
        roleset.setUser_email("TEST7788");
        roleset.setRole_set(MemberRole.USER.toString());
        mapper.insertRoleSet(roleset);
    }

    void searchMember() {
        SearchMemberCriteria sc = new SearchMemberCriteria(1, 10);

        // 이메일 검색
        sc.setType("E");
        sc.setKeyword("jyp");
        List<MemberDTO> member = mapper.searchMember(sc);
        System.out.println(member);

        // 이름 검색
        sc.setType("N");
        sc.setKeyword("박");
        List<MemberDTO> member2 = mapper.searchMember(sc);
        System.out.println(member2);

        // 핸드폰 번호 검색
        sc.setType("T");
        sc.setKeyword("0106");
        List<MemberDTO> member3 = mapper.searchMember(sc);
        System.out.println(member3);

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