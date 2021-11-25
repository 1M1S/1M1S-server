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
        //TODO 이부분 매핑에러 나는데 리프레시토큰관련부분은 알아서 나중에 구현
//        RefreshToken refreshTokenRecord = RefreshToken.builder().member(member).refreshToken(refreshToken).build();
//            refreshTokenRepository.save(refreshTokenRecord);
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
