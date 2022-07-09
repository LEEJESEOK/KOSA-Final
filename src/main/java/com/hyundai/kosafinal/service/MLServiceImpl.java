package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.LogDTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.mapper.userorder.MLMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

@Service
public class MLServiceImpl implements MLService{
    @Autowired
    private MLMapper mapper;

    @Override
    public List<LogDTO> findbyLog(String email) {
        List<LogDTO> result=mapper.findbyLog(email);
        return result;
    }

        @Override
        public  List<LogDTO> getEmailLog(){
            List<LogDTO> result=mapper.getEmailLog();
            return result;
    }

    @Override
    public List<MemberDTO> findbyMember(String email) {
        List<MemberDTO> result=mapper.findbyMember(email);
        return result;
    }
}
