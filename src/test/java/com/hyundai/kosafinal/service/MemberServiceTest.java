package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.LogDTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MLService service;

    @Autowired
    MemberService mservice;
    @Test
    void getEmailLog() {
        List<LogDTO>  result=service.getEmailLog();
        System.out.println(result.get(1));


    }

    @Test
    void insertMember(){
        MemberDTO dto=new MemberDTO();
        boolean result=mservice.insertMember(dto);
    }

    @Test
    void findByLog(){
        String email="AS177253";
        List<LogDTO> result=service.findbyLog(email);
        System.out.println(result);

    }
}