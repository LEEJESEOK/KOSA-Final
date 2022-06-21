package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.MypageReviewDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@Log4j2
@SpringBootTest
public class MypageReviewMapperTest {

    @Autowired
    private MypageReviewMapper mapper;

    @Test
    public void testGetList() {
//        List<MypageReviewDTO> list = mapper.getList("TEST1");
//        log.info(list.toString());
        mapper.getList("TEST1").forEach(
                i -> log.info(i)
        );
    }

    @Test
    public void testGetDetail() {
        log.info(mapper.getDetail(1));
    }

    @Test
    public void testInsert() {
        MypageReviewDTO dto = new MypageReviewDTO(2, "test", "content",
                "대분류", "TEST1", 0, null);
        mapper.insert(dto);
    }
}
