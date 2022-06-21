package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.MemberDTO;

public interface MemberService {
    public boolean insertMember(MemberDTO MemberDTO);//회원가입
    public void deleteMember(String email); //회원삭제
    public void updateMember(MemberDTO member);//회원수정

    public int checkId(String email); //이메일 중복 확인
}
