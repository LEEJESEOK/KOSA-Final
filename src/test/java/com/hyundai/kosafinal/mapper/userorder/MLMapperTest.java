package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.LoginLogDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MLMapperTest {
    @Autowired
    private MLMapper mapper;
    @Test
    void findbyLog() {
    }

    @Test
    void getEmailLog() {

        List<LoginLogDTO> result=mapper.getEmailLog();
        System.out.println(result);
    }

}