package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.*;
import com.hyundai.kosafinal.entity.DateType;
import com.hyundai.kosafinal.entity.SearchMemberCriteria;
import com.hyundai.kosafinal.mapper.userorder.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.*;
import static java.util.Calendar.HOUR;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper mapper;

    @Autowired
    private PasswordEncoder passwordencoder;

    @Override
    public void insertLog(String customer_id) {
        mapper.insertLog(customer_id);
    }

    @Override
    public boolean deleteMember(String email) { //회원삭제
        System.out.println("MemberServiceImpl deleteMember");
        System.out.println(email);

        boolean result = mapper.deleteMember(email);
        System.out.println(result);


        return result;
    }

    @Override
    public String findGrade(String email) {
        String result = mapper.findGrade(email);
        return result;
    }

    @Override
    public boolean updateMember(MemberDTO member) { //회원정보 수정
        System.out.println("서비스임");
        member.setPassword(passwordencoder.encode(member.getPassword()));
        System.out.println(member.getPassword());
        boolean result = mapper.updateMember(member);
        System.out.println(result);
        return result;

    }

    @Override
    public boolean insertMember(MemberDTO member) { //회원가입
        if (mapper.findByEmail(member.getEmail()) != null) {
            return false;
        }
        member.setPassword(passwordencoder.encode(member.getPassword())); //암호 인코딩
        System.out.println(member);
        mapper.insertMember(member); //mapper 연동

        RoleSetDTO roleSet = new RoleSetDTO();
        roleSet.setUser_email(member.getEmail());
        roleSet.setRole_set("USER");
        mapper.insertRoleSet(roleSet);

        return true;
    }


    @Override
    public int checkId(String email) {
        int result = mapper.checkId(email);
        return result;
    }

    @Override
    public String checkPW(String email) {
        String result = mapper.checkPW(email);
        return result;
    }

    @Override
    public VipDTO findByEmail2(String email) {
        VipDTO result = mapper.findByEmail2(email);
        return result;
    }

    @Override
    public Member2DTO findByEmail(String email) {
        return mapper.findByEmail(email);
    }

    // 회원 검색 개수
    @Override
    public int searchMemberCount(SearchMemberCriteria criteria) {
        return mapper.searchMemberCount(criteria);
    }

    // 회원 검색
    @Override
    public List<MemberDTO> searchMember(SearchMemberCriteria criteria) {
        return mapper.searchMember(criteria);
    }

    // vip 검색 개수
    @Override
    public int searchVIPCount(SearchMemberCriteria criteria) {
        return mapper.searchVIPCount(criteria);
    }

    // vip 검색
    @Override
    public List<MemberDTO> searchVIP(SearchMemberCriteria criteria) {
        return mapper.searchVIP(criteria);
    }

    @Override
    public Map<String, Integer> getLoginCountByMemberId(String id, DateType dateType) {
        List<LoginLogDTO> loginLogList = mapper.selectLoginLogByMemberId(id);

        Map<String, Integer> resultMap = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateType.toString());
        for (LoginLogDTO loginLog : loginLogList) {
            Date loginDate = loginLog.getLogin_date();
            resultMap.put(simpleDateFormat.format(loginDate),
                    resultMap.getOrDefault(simpleDateFormat.format(loginDate), 0) + 1);
        }

        paddingIntegerData(resultMap, dateType);

        return resultMap;
    }

    void paddingIntegerData(Map<String, Integer> data, DateType dateType) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateType.toString());

        // 패딩 데이터 추가
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int paddingDateIdx = 0;
        int paddingDate = 0;
        switch (dateType) {
            case YEAR:
                paddingDateIdx = 5;
                paddingDate = YEAR;
                break;
            case MONTH:
                paddingDateIdx = 12;
                paddingDate = MONTH;
                break;
            case DATE:
                paddingDateIdx = 7;
                paddingDate = DATE;
                break;
            case HOUR:
                paddingDateIdx = 24;
                paddingDate = HOUR;
                break;
            case MINUTE:
                paddingDateIdx = 60;
                paddingDate = MINUTE;
                break;
        }

        for (int i = 0; i < paddingDateIdx; ++i) {
            String dateKey = simpleDateFormat.format(calendar.getTime());
            data.put(dateKey, data.getOrDefault(dateKey, 0));
            calendar.add(paddingDate, -1);

        }
    }
}
