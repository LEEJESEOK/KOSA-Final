package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.mapper.userorder.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper mapper;

    @Autowired
    private PasswordEncoder passwordencoder;

    @Override
    public void deleteMember(String email) { //회원삭제
        mapper.deleteMember(email);
    }

    @Override
    public void updateMember(MemberDTO member) { //회원정보 수정
        mapper.updateMember(member);
    }

    @Override
    public boolean insertMember(MemberDTO member)  { //회원가입
        if(mapper.findByEmail(member.getEmail())!=null) {
            return false;
        }
        member.setPassword(passwordencoder.encode(member.getPassword())); //암호 인코딩
        mapper.insertMember(member); //mapper 연동
        return true;
    }
}
