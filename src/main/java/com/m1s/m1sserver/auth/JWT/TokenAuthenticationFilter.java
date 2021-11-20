package com.m1s.m1sserver.auth.JWT;

import com.m1s.m1sserver.UserStorage;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberService;
import com.m1s.m1sserver.auth.refresh_token.RefreshTokenService;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserStorage userStorage;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter)
        throws ServletException, IOException {
        HashMap<String, String> tokens = authenticationTokenProvider.parseTokenString(request);
        String xAccessToken = tokens.get("x-access-token");
        String xRefreshToken = tokens.get("x-refresh-token");
        Long member_id = authenticationTokenProvider.getTokenOwnerNo(xAccessToken);
        Member member = memberService.findMember(member_id);
            if(!authenticationTokenProvider.validateToken
                    (xAccessToken, JwtAuthenticationTokenProvider.getACCESS_PRIVATE_KEY()))
            {
                if(!authenticationTokenProvider.validateToken
                        (xAccessToken, JwtAuthenticationTokenProvider.getREFRESH_PRIVATE_KEY()))
                    throw new CustomException(ErrorCode.INVALID_TOKEN);
                if(!refreshTokenService.checkRefreshToken(member, xRefreshToken))
                    throw new CustomException(ErrorCode.INVALID_TOKEN);
            }
        AuthenticationToken authenticationToken = authenticationTokenProvider.issue(member_id);
        refreshTokenService.updateRefreshToken(member, authenticationToken.getRefreshToken());
        userStorage.setMember(member);
        response.addHeader("x-access-token", authenticationToken.getAccessToken());
        response.addHeader("x-refresh-token", authenticationToken.getRefreshToken());
        filter.doFilter(request, response);
    }
}
