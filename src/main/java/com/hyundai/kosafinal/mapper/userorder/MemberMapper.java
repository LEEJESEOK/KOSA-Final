package com.hyundai.kosafinal.mapper.userorder;
import com.hyundai.kosafinal.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper{
    public boolean insertMember(MemberDTO member); //회원가입
    public void insertRoleSet(RoleSetDTO roleSet); //DB에 회원 권한 삽입
    public boolean deleteRoleSet(String user_email);
    public void loginMember(@Param("email")String email, @Param("password")String password); // 로그인
    public int checkId(String email); //회원가입 시, 중복 여부 체크
    public String checkPW(String email);
    public Member2DTO findByEmail(String email);
    public VipDTO findByEmail2(String email);
    public boolean updateMember(MemberDTO member);
    public boolean deleteMember(String email);
    public void insertGrade(GradeDTO grade);
    public String findGrade(String email);
}
