package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.entity.DateType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Test
    void orderedCountByHour() {
        Map<String, Integer> countMap = orderService.getOrderCountByTime(DateType.MONTH);

        for(String key : countMap.keySet())
            System.out.println(key + " : " + countMap.get(key));
    }

    @Test
    void orderedPriceByHour() {

        Map<String, Integer> priceMap = orderService.getOrderPriceByTime(DateType.DAY);

        for(String key : priceMap.keySet())
            System.out.println(key + " : " + priceMap.get(key));
    }
}