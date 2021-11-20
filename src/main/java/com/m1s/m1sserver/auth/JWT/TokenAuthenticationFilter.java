package com.m1s.m1sserver.auth.JWT;

import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter)
        throws ServletException, IOException{
        HashMap<String, String> tokens = authenticationTokenProvider.parseTokenString(request);
        String xAccessToken = tokens.get("x-access-token");
        String xRefreshToken = tokens.get("x-refresh-token");
    }
}
