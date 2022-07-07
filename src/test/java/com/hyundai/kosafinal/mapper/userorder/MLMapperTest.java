package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.LogDTO;
import com.hyundai.kosafinal.mapper.userorder.MLMapper;
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

        List<LogDTO> result=mapper.getEmailLog();
        System.out.println(result);
    }

}