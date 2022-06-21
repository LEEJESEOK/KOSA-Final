package com.hyundai.kosafinal.mapper.userorder;
import com.hyundai.kosafinal.domain.GradeDTO;
import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.RoleSetDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper{
    public boolean insertMember(MemberDTO member); //회원가입
    public void insertRoleSet(RoleSetDTO roleSet); //DB에 회원 권한 삽입
    public void loginMember(@Param("email")String email, @Param("password")String password); // 로그인
    public int checkId(String email); //회원가입 시, 중복 여부 체크
    public Member2DTO findByEmail(String email);
    public void updateMember(MemberDTO member);
    public void deleteMember(String email);
    public void insertGrade(GradeDTO grade);




    //boolean checkID(boolean b);
}
