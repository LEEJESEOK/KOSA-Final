package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.VipDTO;
import com.hyundai.kosafinal.entity.SearchMemberCriteria;

import java.util.List;

public interface MemberService {
    public boolean insertMember(MemberDTO MemberDTO);//회원가입

    public boolean deleteMember(String email); //회원삭제

    public boolean updateMember(MemberDTO member);//회원수정

    public String checkPW(String email);

    public String findGrade(String email);

    Member2DTO findByEmail(String email);

    VipDTO findByEmail2(String email);

    public int checkId(String email); //이메일 중복 확인

    public int searchMemberCount(SearchMemberCriteria criteria); // 회원 검색 개수

    public List<MemberDTO> searchMember(SearchMemberCriteria criteria); // 회원 검색

    public int searchVIPCount(SearchMemberCriteria criteria); // vip 검색 수

    public List<MemberDTO> searchVIP(SearchMemberCriteria criteria); // vip 검색
}
