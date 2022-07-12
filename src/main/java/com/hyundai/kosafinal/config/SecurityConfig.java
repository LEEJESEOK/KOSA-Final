package com.hyundai.kosafinal.config;


/*************************************************************
 파일명: SecurityConfig.java
 기능: Security 를 사용하기 위한 로그인 설정
 작성자: 이승연
 [코멘트: 일반 사용자 로그인, 소셜로그인 그리고 접근제한을 설정할 수 있다.]
 ***********************************************************/

import com.hyundai.kosafinal.entity.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Log4j2
//Security 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationSuccessHandler customSuccessHandler;

    // Bean 등록
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchyImpl() {
        log.info("실행");
        RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
        roleHierarchyImpl.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER");
        return roleHierarchyImpl;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/cart/**").hasRole(MemberRole.USER.name())
                .antMatchers("/order/**").hasRole(MemberRole.USER.name())
                .antMatchers("/mypage/**").hasRole(MemberRole.USER.name())
                .antMatchers("/biz/**").hasRole(MemberRole.ADMIN.name());

        //일반 사용자 로그인
        http.formLogin().loginPage("/member/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email") //DB의 Member 테이블과 연동하기 위해 username을 email로 설정한다.
                .passwordParameter("password")//DB의 Member 테이블과 연동하기 위해 password를 password 로 설정한다.
                .defaultSuccessUrl("/")//로그인 성공 후 이동할 URL
                .successHandler(customSuccessHandler)
                .failureUrl("/member/login?error=true"); //로그인 실패시 URL

        //소셜 로그인 (구글)
        http.oauth2Login().loginPage("/member/login").defaultSuccessUrl("/");

        //소셜 로그인 (네이버)

        //소셜 로그인 (카카오)

        // csrf 설정
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        //로그아웃
        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/");


    }

}

