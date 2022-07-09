package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.LoginLogDTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MLMapper {
    public List<LoginLogDTO> findbyLog(String email);

    public List<LoginLogDTO> getEmailLog();

    public List<MemberDTO> findbyMember(String email); //로그 테이블에서 중복을 제외하고 모든 회원  id 가져오기
}
