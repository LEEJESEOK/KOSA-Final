package com.hyundai.kosafinal.security;


/*************************************************************
 파일명: OAuth2UserDetailsService.java
 기능: 소셜 로그인 기능 구현
 작성자: 이승연
 *************************************************************/


import lombok.extern.log4j.Log4j2;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hyundai.kosafinal.domain.AuthMemberDTO;
import com.hyundai.kosafinal.domain.MemberDTO;
import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.entity.MemberRole;
import com.hyundai.kosafinal.domain.RoleSetDTO;
import com.hyundai.kosafinal.mapper.userorder.MemberMapper;

@Log4j2
@Service
public class OAuth2UserDetailService extends DefaultOAuth2UserService{
    @Autowired
    private MemberMapper mapper;
    // 패스워드 암호화
    @Autowired
    private  PasswordEncoder passwordEncoder;

    private Member2DTO saveSocialMember(String email){

        // 이메일 중복확인
        Member2DTO result = mapper.findByEmail(email);
        // 기본 회원이면 해당 정보 반환
        if (!(result == null)) {
            return  result;
        }
        // 패스워드 1111 이름은 이메일주소 (가입한 적이 없다면)
        MemberDTO member = new MemberDTO();
        member.setEmail(email);
        member.setName(email);
        member.setPassword(passwordEncoder.encode("1111"));
        member.setLogin_type(1);
        // Member2 저장
        mapper.insertMember(member);
        RoleSetDTO role = new RoleSetDTO();
        role.setUser_email(email);
        role.setRole_set(MemberRole.USER.toString());
        // RoleSet 저장
        mapper.insertRoleSet(role);
        result = mapper.findByEmail(email);
        return result;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        log.info("-------loaduser-------------");

        String clienName = userRequest.getClientRegistration().getClientName();
        // 인증  출력
        log.info(userRequest.getAdditionalParameters());

        // 사용자 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("======oAuth2User===============");

        // 신규회원 테이블에 저장 시작
        String email = null;
        if (clienName.equals("Google")) {// 구글 인증 확인
            email = oAuth2User.getAttribute("email");
        } // end
        Member2DTO result = null;
        try {
            result = saveSocialMember(email);
            log.info("---saveSocialMember--");
            log.info(result);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_"+result.getRole_set()));
            AuthMemberDTO authMemberDTO = new AuthMemberDTO(result.getEmail(), result.getPassword(),
                    result.getName(),result.getLogin_type(),authorities);
            authMemberDTO.setEmail(result.getEmail());
            authMemberDTO.setName(result.getName());
            authMemberDTO.setLogin_type(result.getLogin_type());
           // authMemberDTO.setPassword(result.getPassword());
            log.info(authMemberDTO.getAuthorities().toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("saveSocialMember error");
        }//end
        return oAuth2User;
    }

}// end

