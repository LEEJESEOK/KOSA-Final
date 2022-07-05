package com.hyundai.kosafinal.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/")
public class MLController {
    @GetMapping("/test1")
    public void test1() {
        log.info("usual_login......");
    }
}
