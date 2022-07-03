package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.RoleSetDTO;
import com.hyundai.kosafinal.mapper.userorder.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper mapper;

    @Autowired
    private PasswordEncoder passwordencoder;

    @Override
    public boolean deleteMember(String email) { //회원삭제
        System.out.println("MemberServviceImpl deleteMember");
        System.out.println(email);

        boolean result=mapper.deleteMember(email);
        System.out.println(result);


        return result;
    }

    @Override
    public boolean updateMember(MemberDTO member) { //회원정보 수정
        System.out.println("서비스임");
        member.setPassword(passwordencoder.encode(member.getPassword()));
        System.out.println(member.getPassword());
        boolean result = mapper.updateMember(member);
        System.out.println(result);
        return result;

    }

    @Override
    public boolean insertMember(MemberDTO member)  { //회원가입
        if(mapper.findByEmail(member.getEmail())!=null) {
            return false;
        }
        member.setPassword(passwordencoder.encode(member.getPassword())); //암호 인코딩
        System.out.println(member);
        mapper.insertMember(member); //mapper 연동

        RoleSetDTO roleset=new RoleSetDTO();
        roleset.setUser_email(member.getEmail());
        roleset.setRole_set("USER");
        mapper.insertRoleSet(roleset);

        return true;
    }


    @Override
    public int checkId(String email){
        int result=mapper.checkId(email);
        return result;
    }

    @Override
    public String checkPW(String email){
        String result=mapper.checkPW(email);
        return result;
    }

    @Override
    public Member2DTO findByEmail(String email) {
        return mapper.findByEmail(email);
    }
}
