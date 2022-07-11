package com.hyundai.kosafinal.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Log4j2
@Controller
@RequestMapping("/vip")
public class VipController {

    // 라운지 페이지 이동
    @RequestMapping(value = "/lounge" , method = RequestMethod.GET)
    public void lounge() {
        log.info("VIP Lounge로 이동");
    }

}
