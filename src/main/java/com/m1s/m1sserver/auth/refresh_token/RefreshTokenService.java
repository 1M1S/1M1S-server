package com.m1s.m1sserver.auth.refresh_token;

import com.m1s.m1sserver.auth.JWT.AuthenticationToken;
import com.m1s.m1sserver.auth.JWT.JwtAuthenticationTokenProvider;
import com.m1s.m1sserver.auth.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    JwtAuthenticationTokenProvider jwtAuthenticationTokenProvider;

    public void insertRefreshToken(Member member, String refreshToken){
        RefreshToken refreshTokenRecord = RefreshToken.builder().member(member).refreshToken(refreshToken).build();
        refreshTokenRepository.save(refreshTokenRecord);
    }

    public void updateRefreshToken(RefreshToken refreshToken, String newRefreshToken){
        refreshToken.setRefreshToken(newRefreshToken);
        refreshTokenRepository.save(refreshToken);
    }

    public void deleteRefreshToken(Member member){
        if(!refreshTokenRepository.existsByMember(member));
        refreshTokenRepository.deleteByMember(member);
    }
}
