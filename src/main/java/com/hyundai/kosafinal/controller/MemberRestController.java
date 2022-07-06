package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.PageDTO;
import com.hyundai.kosafinal.entity.SearchCriteria;
import com.hyundai.kosafinal.entity.SearchMemberCriteria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.hyundai.kosafinal.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@RestController
@RequestMapping("/member")
public class MemberRestController {
    @Autowired
    MemberService service;

    @Autowired
    private PasswordEncoder passwordencoder;

    @RequestMapping(value = "/register", method = RequestMethod.POST) //회원가입
    public void register(@RequestBody Map<String, Object> params) throws ParseException {
        System.out.println("----------------------------");
        System.out.println("회원가입 컨트롤러 ");
        for (String key : params.keySet())
            System.out.println(key + " : " + params.get(key));

        MemberDTO member = new MemberDTO();

        member.setEmail((String) params.get("email"));
        member.setPassword((String) params.get("password"));
        member.setName((String) params.get("name"));

        System.out.println(params.get("gender"));
        if(params.get("gender")!=null){ //
            member.setGender((Integer) params.get("gender"));
        }



        member.setTel((String) params.get("tel"));
        member.setAddress1((String) params.get("address1"));
        member.setAddress2((String) params.get("address2"));
        member.setZipcode((String) params.get("zipcode"));

        if(params.get("height")!=""){
            member.setHeight(Integer.parseInt((String) params.get("height")));
        }
        else{
            member.setHeight(0);
        }
        if(params.get("weight")!=""){
            member.setWeight(Integer.parseInt((String) params.get("weight")));
        }
        else{
            member.setWeight(0);
        }



        Date date = new Date();
        String dateStr = (String) params.get("birth1") + "-" + (String) params.get("birth2") + "-" + (String) params.get("birth3");     // 포맷터
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");         // 문자열 -> Date
        date = formatter.parse(dateStr);
        member.setBirth(date);


        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", service.insertMember(member) ? "success" : "fail");


    }

    @RequestMapping(value = "/isDuplicate", method = RequestMethod.GET) // 이메일 중복확인
    public String isDuplicate(HttpServletRequest request) {
        System.out.println("----------------------------");
        System.out.println("이메일 중복체크");
        String email = request.getParameter("email");
        int result = service.checkId(email);
        if (result == 1) {
            return "true";
        } else {
            return "false";
        }
    }

    @RequestMapping(value = "/isDuplicate2", method = RequestMethod.GET) // 기존 비밀번호랑 비교
    public String isDuplicate2(HttpServletRequest request) {
        System.out.println("-----------------------------");
        System.out.println("기존 비밀번호랑 비교");
        String email = request.getParameter("email"); //기존 이메일
        System.out.println(email);
        String old_password = service.checkPW(email); //기존 비밀번호
        System.out.println(old_password);
        String new_password = request.getParameter("password"); //새로 입력받은 비밀번호


        if (passwordencoder.matches(new_password, old_password)) {
            return "true";
        } else {
            return "false";
        }
    }


    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public boolean delete(@RequestBody Map<String,Object> params) {
        for (String key : params.keySet())
            System.out.println(key + " : " + params.get(key));

        return service.deleteMember((String)params.get("email"));
    }

    // 회원 검색
    @RequestMapping(value="/searchMember", method = RequestMethod.POST)
    public Map<String, Object> searchMember(@RequestBody SearchMemberCriteria criteria) {
        Map<String, Object> map = new HashMap<>();

        int count = service.searchMemberCount(criteria);
        List<MemberDTO> list = service.searchMember(criteria);

        PageDTO pageDTO = new PageDTO(criteria, count);
        log.info("회원 검색: " + list);

        map.put("list", list);
        map.put("page", pageDTO);

        return map;
    }

    // 회원 검색
    @RequestMapping(value="/searchVIP", method = RequestMethod.POST)
    public Map<String, Object> searchVIP(@RequestBody SearchMemberCriteria criteria) {
        Map<String, Object> map = new HashMap<>();

        int count = service.searchMemberCount(criteria);
        List<MemberDTO> list = service.searchMember(criteria);

        PageDTO pageDTO = new PageDTO(criteria, count);
        log.info("VIP 회원 검색: " + list);

        map.put("list", list);
        map.put("page", pageDTO);

        return map;
    }
}
