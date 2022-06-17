/*************************************************************
 파일명: signUpService.java
 기능: 회원가입 서비스
 작성자: 이승연
 *************************************************************/
package com.hyundai.kosafinal.service;


import com.hyundai.kosafinal.domain.MemberDTO;

public interface SignUpService {
    public boolean insertMember(MemberDTO MemberDTO);
}
