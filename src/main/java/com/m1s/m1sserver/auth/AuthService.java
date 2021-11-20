package com.m1s.m1sserver.auth;


import com.m1s.m1sserver.auth.JWT.AuthenticationToken;
import com.m1s.m1sserver.auth.JWT.JwtAuthenticationTokenProvider;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberService;
import com.m1s.m1sserver.api.user.information.MemberInformation;
import com.m1s.m1sserver.api.user.information.MemberInformationService;
import com.m1s.m1sserver.auth.refresh_token.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberInformationService memberInformationService;

    @Autowired
    JwtAuthenticationTokenProvider jwtAuthenticationTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String encodePassword(String password){return passwordEncoder.encode(password);}

    public void join(MemberInformation memberInformation){
        memberInformationService.insertMemberInformaion(memberInformation);
    }

    public AuthenticationToken login(Member member){
        if(!memberService.loginInformationCheck(member.getUsername(), member.getPassword()));
        AuthenticationToken authenticationToken = jwtAuthenticationTokenProvider.issue(member.getId());
        refreshTokenService.insertRefreshToken(member, authenticationToken.getRefreshToken());
        return authenticationToken;
    }

    public void checkOwner(Member user, Member target){
        if(user.getId() == target.getId())return;
    }

    public void checkPassword(Member user, String password){
        if(user.getPassword() != encodePassword(password));
    }
    public void logout(Member member){
        refreshTokenService.deleteRefreshToken(member);
        return;
    }
}
