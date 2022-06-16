package com.hyundai.kosafinal.mapper;
import com.hyundai.kosafinal.domain.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    public void insertMember(MemberDTO member); //회원가입
    public void selectMember(@Param ("email")String email,@Param ("password")String password); // 로그인
    public void checkId(String email); //회원가입 시, 중복 여부 체크
}
