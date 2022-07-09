package com.hyundai.kosafinal.controller;


import com.hyundai.kosafinal.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Log4j2
@Controller
@RequestMapping("/vip")
public class VIPController {

    @RequestMapping(value = "/lounge" , method = RequestMethod.GET) // 라운지 페이지 이동
    public void lounge() {
        log.info("VIP Lounge로 이동");
    }

}
