package com.m1s.m1sserver.auth.refresh_token;

import com.m1s.m1sserver.auth.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository {
    RefreshToken findByMemberAndRefreshToken(Member member, String refreshToken);
    boolean existsByMember(Member member);
    void deleteByMember(Member member);
}
