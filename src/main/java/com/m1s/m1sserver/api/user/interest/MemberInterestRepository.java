package com.m1s.m1sserver.api.user.interest;

import com.m1s.m1sserver.api.user.interest.MemberInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInterestRepository extends JpaRepository<MemberInterest, Long> {
    Iterable<MemberInterest> findAllByMemberId(long user_id);
}
