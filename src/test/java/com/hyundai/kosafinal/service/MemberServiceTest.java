package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.LoginLogDTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.entity.DateType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MLService mlService;

    @Autowired
    MemberService memberService;

    @Test
    void getEmailLog() {
        List<LoginLogDTO> result = mlService.getEmailLog();
        System.out.println(result.get(1));


    }

    @Test
    void insertMember() {
        MemberDTO dto = new MemberDTO();
        boolean result = memberService.insertMember(dto);
    }

    @Test
    void findByLog() {
        String email = "AS177253";
        List<LoginLogDTO> result = mlService.findbyLog(email);
        System.out.println(result);

    }

    @Test
    void getLoginLogByMemberId() {
        String id = "jadyn";
        DateType dateType = DateType.HOUR;
        String dateTypeStr = "HOUR";
        System.out.println(DateType.valueOf(dateTypeStr));

        Map<String, Integer> loginLogList = memberService.getLoginCountByMemberId(id, dateType);

        for (String key : loginLogList.keySet()) {
            System.out.println(key + " : " + loginLogList.get(key));
        }
    }
}