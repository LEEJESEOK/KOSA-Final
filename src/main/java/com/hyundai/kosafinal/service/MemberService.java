package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.MemberDTO;

public interface MemberService {
    public boolean insertMember(MemberDTO MemberDTO);//회원가입
    public boolean deleteMember(String email); //회원삭제
    public boolean updateMember(MemberDTO member);//회원수정
    public String checkPW(String email);
    Member2DTO findByEmail(String email);
    public int checkId(String email); //이메일 중복 확인
}
