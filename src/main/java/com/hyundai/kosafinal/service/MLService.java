package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.LogDTO;
import com.hyundai.kosafinal.domain.MemberDTO;

import java.util.List;
import java.util.Map;


public interface MLService {
    public List<LogDTO> findbyLog(String email);
    public  List<LogDTO> getEmailLog();
    public List<MemberDTO> findbyMember(String email);
}
