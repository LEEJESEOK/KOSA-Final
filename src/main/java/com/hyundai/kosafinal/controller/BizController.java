package com.hyundai.kosafinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/biz")
public class BizController {

    @RequestMapping(value = {"", "/"})
    public String index() {
        return "biz/index";
    }

}
