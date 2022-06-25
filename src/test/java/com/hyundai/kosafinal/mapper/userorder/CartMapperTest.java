package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.CartDTO;
import com.hyundai.kosafinal.domain.MypageReviewDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Log4j2
@SpringBootTest
public class CartMapperTest {

    @Autowired
    private CartMapper mapper;

    @Test
    public void testGetList() {
        mapper.getList("TEST1").forEach(
                i -> log.info(i)
        );
    }


//    @Test
//    public void testInsert() {
//        CartDTO dto = new CartDTO(0, "MM2B3WJS085M", "DN", "42", 1, "TEST1");
//        mapper.insert(dto);
//    }
//
//    @Test
//    public void testUpdate() {
//        CartDTO dto = new CartDTO(2, "MM2B3WJS085M", "DN", "44", 1, "TEST1");
//        mapper.update(dto);
//    }
//
//    @Test
//    public void testDelete() {
//        mapper.delete(2, "TEST1");
//    }
}
