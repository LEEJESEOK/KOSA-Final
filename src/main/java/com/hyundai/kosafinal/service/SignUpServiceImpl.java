package com.hyundai.kosafinal.service;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.RoleSetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.hyundai.kosafinal.mapper.userorder.MemberMapper;

@Service
public class SignUpServiceImpl implements SignUpService{
    @Autowired
    private MemberMapper mapper;

    @Autowired
    private PasswordEncoder passwordencoder;

    public boolean insertMember(MemberDTO MemberDTO) {
        MemberDTO.setPassword(passwordencoder.encode(MemberDTO.getPassword())); //암호 인코딩
        mapper.insertMember(MemberDTO); //mapper 연동
        RoleSetDTO RoleSetDTO = new RoleSetDTO("USER",MemberDTO.getEmail()); //RoleSet 세팅
        mapper.insertRoleSet(RoleSetDTO);//권한 삽입
        return true;
    }

}
