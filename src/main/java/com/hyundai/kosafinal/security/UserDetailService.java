package com.hyundai.kosafinal.security;

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
        log.info("userDetailsService...............");
        // 입력한 이메일로 Member 찾음
        Member2DTO result = null;
        try {
            log.info(email);

            result = mapper.findByEmail(email);

        } catch (Exception e) {
            e.printStackTrace();
            //로직 처리만 0이면 가입, 1이면 탈퇴, 2면 휴면상태
            throw new UsernameNotFoundException("Check Email or Social!!");
        } // end try

        log.info("result"+result);
        log.info("role set : " +result.getRole_set());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + result.getRole_set()));

        // clubMember --> AuthMemberDTO 변환
        AuthMemberDTO  authMemberDTO = new
                AuthMemberDTO(result.getEmail(), result.getPassword(),result.getName(),
                result.getLogin_type(), authorities);

        authMemberDTO.setPassword(result.getPassword());

        log.info(authMemberDTO);
        log.info(authMemberDTO.getAuthorities());
        // AuthMemberDTO는 UserDetails 타입으로 처리됨
        return authMemberDTO;
    }// end load..

}// end Cla...


