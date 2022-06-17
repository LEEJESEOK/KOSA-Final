package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.mapper.userorder.MemberMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.AuthMemberDTO;

@Service
@Log4j2

public class UserDetailService implements UserDetailsService {
    @Autowired
    private MemberMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        // 입력한 이메일로 Member 찾음
        Member2DTO result = null;
        try {
            log.info(email);
            result = mapper.findByEmail(email);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Check Email or Social!!");
        } // end try

        // Member2 생성
        Member2DTO Member2DTO = result;
        log.info("-------------------");
        log.info(Member2DTO);
        log.info(Member2DTO.getRole_set().toString());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + Member2DTO.getRole_set()));

        // clubMember --> AuthMemberDTO 변환
        AuthMemberDTO  AuthMemberDTO = new
                AuthMemberDTO(Member2DTO.getEmail(), Member2DTO.getPassword(),
                Member2DTO.getLogin_type(), authorities);
        // AuthMemberDTO 값 세팅
        AuthMemberDTO.setName((Member2DTO.getName()));
        AuthMemberDTO.setLogin_type(Member2DTO.getLogin_type());

        log.info(AuthMemberDTO);
        log.info(AuthMemberDTO.getAuthorities());

        // AuthMemberDTO는 UserDetails 타입으로 처리됨
        return AuthMemberDTO;
    }// end load..

}// end Cla...


