package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.MypageReviewDTO;
import com.hyundai.kosafinal.domain.ReplyDTO;
import com.hyundai.kosafinal.service.MemberService;
import com.hyundai.kosafinal.service.MypageReviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/mypage")
public class MypageRestController {

    // 필요한 서비스 자동 주입
    @Autowired
    private MypageReviewService service;

    @Autowired
    MemberService memberservice;

    // 1:1 문의 게시글 목록 조회
    @GetMapping("/review/all")
    public ResponseEntity<List<MypageReviewDTO>> getMypageReviewList(@AuthenticationPrincipal AuthMemberDTO authentication) {
        // 회원 정보 찾기
        String userEmail = authentication.getEmail();
        ResponseEntity<List<MypageReviewDTO>> entry = null;
        entry = new ResponseEntity<List<MypageReviewDTO>>(service.getList(userEmail), HttpStatus.OK);
        log.info("문의글 목록 : " + entry);

        return entry;
    }

    // 1:1 문의 작성
    @PostMapping("/review/register")
    public boolean insert(@RequestBody MypageReviewDTO dto, @AuthenticationPrincipal AuthMemberDTO authentication) {
        // 회원 정보 찾기
        String userEmail = authentication.getEmail();
        dto.setUserEmail(userEmail);
        service.insert(dto);
        log.info("문의글 작성 : " + dto);

        return true;
    }

    // 1:1 문의 답글 조회
    @GetMapping("/review/reply/{id}")
    public ResponseEntity<ReplyDTO> getMypageReviewReply(@PathVariable int id) {
        ResponseEntity<ReplyDTO> entry = null;
        entry = new ResponseEntity<ReplyDTO>(service.getReply(id), HttpStatus.OK);
        log.info("문의 답글 : " + entry);

        return entry;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST) //업데이트
    public boolean update(@RequestBody Map<String, Object> params) throws ParseException {
        System.out.println("----------------------------");
        System.out.println("회원정보수정");
        for (String key : params.keySet())
            System.out.println(key + " : " + params.get(key));
        MemberDTO member = new MemberDTO();
        member.setEmail((String) params.get("email"));
        member.setName((String) params.get("name"));
        member.setTel((String)params.get("tel"));
        member.setPassword((String) params.get("password"));
        member.setAddress1((String)params.get("address1"));
        member.setAddress2((String)params.get("address2"));
        if(params.get("gender")!=null){ //
            member.setGender((Integer) params.get("gender"));
        }

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
        System.out.println("레스트" + member);
        if (!((String) params.get("birth1")).equals(null) || !params.get("birth2").equals(null) || !params.get("birth3").equals(null)) {

            Date date = new Date();
            String dateStr = (String) params.get("birth1") + "-" + (String) params.get("birth2") + "-" + (String) params.get("birth3");     // 포맷터
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");         // 문자열 -> Date
            date = formatter.parse(dateStr);
            member.setBirth(date);
        }
        return memberservice.updateMember(member);


    }

}
