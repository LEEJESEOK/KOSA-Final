package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.LoginLogDTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.mapper.userorder.MLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MLServiceImpl implements MLService{
    @Autowired
    private MLMapper mapper;

    @Override
    public List<LoginLogDTO> findbyLog(String email) {
        List<LoginLogDTO> result=mapper.findbyLog(email);
        return result;
    }

        @Override
        public  List<LoginLogDTO> getEmailLog(){
            List<LoginLogDTO> result=mapper.getEmailLog();
            return result;
    }

    @Override
    public List<MemberDTO> findbyMember(String email) {
        List<MemberDTO> result=mapper.findbyMember(email);
        return result;
    }
}
