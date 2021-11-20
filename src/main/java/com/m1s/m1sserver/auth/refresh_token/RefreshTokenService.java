package com.m1s.m1sserver.auth.refresh_token;

import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public void insertRefreshToken(Member member, String refreshToken){
        RefreshToken refreshTokenRecord = RefreshToken.builder().member(member).refreshToken(refreshToken).build();
            refreshTokenRepository.save(refreshTokenRecord);

    }

    public void updateRefreshToken(Member member, String newRefreshToken){
        RefreshToken refreshToken = findRefreshToken(member);
        refreshToken.setRefreshToken(newRefreshToken);
        refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findRefreshToken(Member member){
        return refreshTokenRepository.findByMember(member).get();
    }
    public boolean checkRefreshToken(Member member, String refreshToken){
        return refreshTokenRepository.existsByMemberAndRefreshToken(member, refreshToken);
    }
    public void deleteRefreshToken(Member member){
        if(!refreshTokenRepository.existsByMember(member))throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        refreshTokenRepository.deleteByMember(member);
    }
}
