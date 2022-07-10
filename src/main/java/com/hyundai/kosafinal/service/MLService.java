package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.LoginLogDTO;
import com.hyundai.kosafinal.domain.MemberDTO;

import java.util.List;


public interface MLService {
    public List<LoginLogDTO> findbyLog(String email);
    public  List<LoginLogDTO> getEmailLog();
    public List<MemberDTO> findbyMember(String email);
}
