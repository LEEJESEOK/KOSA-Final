package com.hyundai.kosafinal.controller;


import com.hyundai.kosafinal.domain.AuthMemberDTO;
import com.hyundai.kosafinal.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Log4j2
@Controller
public class VipController {

    @Autowired
    private MemberService service;

    // 라운지 페이지 이동
    @RequestMapping(value = "/vip/lounge" , method = RequestMethod.GET)
    public String lounge(@AuthenticationPrincipal AuthMemberDTO authentication) {
        String userEmail = authentication.getEmail();
        int grade_id = Integer.parseInt(service.findGrade(userEmail));

        if(grade_id != 4 && grade_id != 5) {
            return "error/All_error";
        }
        log.info("VIP Lounge로 이동");
        return "vip/lounge";
    }

}
