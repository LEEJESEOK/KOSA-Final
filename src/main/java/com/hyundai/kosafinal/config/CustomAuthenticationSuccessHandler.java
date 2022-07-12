package com.hyundai.kosafinal.config;

import com.hyundai.kosafinal.service.MemberService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    RequestCache requestCache = new HttpSessionRequestCache();

    final
    MemberService memberService;

    public CustomAuthenticationSuccessHandler(@Lazy MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        memberService.insertLog(authentication.getName());


        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            // 인증 받기 전 url로 이동하기
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            // 기본 url로 가도록 함함
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }


    }
}