package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.MemberDTO;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.hyundai.kosafinal.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberRestController {
    @Autowired
    MemberService service;

    @Autowired
    private PasswordEncoder passwordencoder;

    @RequestMapping(value = "/register", method = RequestMethod.POST) //회원가입
    public void register(@RequestBody Map<String, Object> params) {

        for (String key : params.keySet())
            System.out.println(key + " : " + params.get(key));
        System.out.println(params.size());
        System.out.println("회원가입 컨트롤러 ");
        MemberDTO member = new MemberDTO();

        System.out.println("----------------------------");
        member.setEmail((String) params.get("email"));
        System.out.println((String) params.get("email"));

        member.setPassword((String) params.get("password"));
        System.out.println((String) params.get("password"));

        member.setName((String) params.get("name"));
        System.out.println((String) params.get("name"));

        member.setGender((Integer) params.get("gender"));
        System.out.println(params.get("gender"));


        System.out.println(params.get("birth1"));
        System.out.println(params.get("birth2"));
        System.out.println(params.get("birth3"));
        Date date=new Date();
        date.setYear(Integer.parseInt((String)params.get("birth1")));
        date.setMonth(Integer.parseInt((String)params.get("birth2")));
        date.setDate(Integer.parseInt((String)params.get("birth3")));
        member.setBirth(date);

        member.setTel((String) params.get("tel"));
        System.out.println( params.get("tel"));

        member.setAddress1((String) params.get("address1"));
        System.out.println( params.get("address1"));

        member.setAddress2((String) params.get("address2"));
        System.out.println( params.get("address2"));

        member.setZipcode((String) params.get("zipcode"));
        System.out.println( params.get("zipcode"));

        member.setHeight(Integer.parseInt((String)params.get("height")));
        System.out.println( params.get("height"));

        member.setWeight(Integer.parseInt((String)params.get("weight")));
        System.out.println( params.get("weight"));

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", service.insertMember(member) ? "success" : "fail");


    }

    @RequestMapping(value = "/isDuplicate", method = RequestMethod.GET) //중복확인
    public String isDuplicate(HttpServletRequest request) {
        System.out.println("이메일 중복체크");
        String email = request.getParameter("email");
        int result = service.checkId(email);
        if (result == 1) {
            return "true";
        } else {
            return "false";
        }
    }

}
