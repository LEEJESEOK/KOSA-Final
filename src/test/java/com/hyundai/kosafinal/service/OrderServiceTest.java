package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.entity.DateType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    OrderService orderService;
    static Calendar calendar;

    @BeforeAll
    void InitClass() {
        calendar = Calendar.getInstance();
    }

    @Test
    void getSalesCount() {
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Map<String, Integer> countMap = orderService.getSalesCount(calendar.getTime(), DateType.MONTH);

        for (String key : countMap.keySet())
            System.out.println(key + " : " + countMap.get(key));
    }

    @Test
    void getRevenue() {
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Map<String, Integer> priceMap = orderService.getRevenue(calendar.getTime(), DateType.MONTH);

        for (String key : priceMap.keySet())
            System.out.println(key + " : " + priceMap.get(key));
    }

    @Test
    void getCustomerCount() {
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Map<String, Integer> priceMap = orderService.getCustomerCount(calendar.getTime(), DateType.MONTH);

        for (String key : priceMap.keySet())
            System.out.println(key + " : " + priceMap.get(key));
    }
}