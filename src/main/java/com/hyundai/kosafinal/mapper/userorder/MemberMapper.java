package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.*;
import com.hyundai.kosafinal.entity.SearchMemberCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    public boolean insertMember(MemberDTO member); //회원가입

    public void insertRoleSet(RoleSetDTO roleSet); //DB에 회원 권한 삽입

    public boolean deleteRoleSet(String user_email);

    public void loginMember(@Param("email") String email, @Param("password") String password); // 로그인

    public int checkId(String email); //회원가입 시, 중복 여부 체크

    public String checkPW(String email);

    public Member2DTO findByEmail(String email);

    public VipDTO findByEmail2(String email);

    public boolean updateMember(MemberDTO member);

    public boolean deleteMember(String email);

    public void insertGrade(GradeDTO grade);

    public String findGrade(String email);

    public int searchMemberCount(SearchMemberCriteria criteria); // 회원 필터링 검색 수

    public List<MemberDTO> searchMember(SearchMemberCriteria criteria); // 회원 필터링 검색

    public int searchVIPCount(SearchMemberCriteria criteria); // vip 검색 수

    public List<MemberDTO> searchVIP(SearchMemberCriteria criteria); // vip 검색

    public void insertLog(String customer_id);

    List<LoginLogDTO> selectLoginLogByMemberId(String memberId);
}
