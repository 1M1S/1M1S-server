package com.m1s.m1sserver.auth.JWT;

import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberService;
import com.m1s.m1sserver.auth.refresh_token.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


/**
 * edited by refcell22 at 2021-11-25 (refcell22@naver.com)
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RefreshTokenService refreshTokenService;


    //refcell22 - 유저스토리지에 유저를 담는것은 잘못된 구현임. 이렇게 하겠다면 서버 내에 단 한명만 로그인이 가능하게되고, 궂이 한다면 접속자 관리 시스템을 직접 구현하겠다는건데
    //이부분은 스프링시큐리티에서 알아서 하니 안쓰는게 맞다. UserStorage는 추후 지울것
//    @Autowired
//    private UserStorage userStorage;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter)
        throws ServletException, IOException {
        HashMap<String, String> tokens = authenticationTokenProvider.parseTokenString(request);
        //토큰없을경우 권한없음으로 통과
        if(tokens == null){
            filter.doFilter(request,response);
            return;
        }

        String xAccessToken = tokens.get("x-access-token");
        String xRefreshToken = tokens.get("x-refresh-token");
        Long member_id = authenticationTokenProvider.getTokenOwnerNo(xAccessToken);
        Member member = memberService.findMember(member_id);

        //리프레시토큰이 헤더에 있는 경우에만 토큰 갱신 처리
        //TODO 알아서 처리할것, 이부분은 구현안함
        if(refreshTokenService.checkRefreshToken(member,xRefreshToken)){
//            refreshTokenService.updateRefreshToken(member, authenticationToken.getRefreshToken());
//            AuthenticationToken authenticationToken = authenticationTokenProvider.issue(member_id);
//        response.addHeader("x-access-token", aunticationToken.getAccessToken());
//        response.addHeader("x-refresh-token", authenticationToken.getRefreshToken());
        }

        //토큰이 있다면 적절한 토큰인지 확인하고, 적절하다면 권한 인정, 적절하지 않을경우 권한없음으로 통과
        Jws<Claims> claims = authenticationTokenProvider.parseToken(xAccessToken, JwtAuthenticationTokenProvider.getACCESS_PRIVATE_KEY());
        if(claims == null){
            //잘못된 토큰일경우 필터를 권한없이 통과
            filter.doFilter(request,response);
            return;
        }

        //이미 로그인된상태로 토큰이 적절한경우 토큰을 Authentication으로 변환하여 스프링 시큐리티에 전달, 이후 권한검사는 스프링시큐리티가 알아서함
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(claims));
        filter.doFilter(request, response);
    }
}
