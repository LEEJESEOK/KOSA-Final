package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.LogDTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MLMapper {
    public List<LogDTO> findbyLog(String email);
    public List<LogDTO> getEmailLog();
    public  List<MemberDTO> findbyMember(String email); //로그 테이블에서 중복을 제외하고 모든 회원  id 가져오기
}
