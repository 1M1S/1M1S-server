package com.m1s.m1sserver.api.user.information;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInformationRepository extends JpaRepository<MemberInformation, Long> {
    MemberInformation findByMemberId(Long user_id);
    boolean existsByEmailOrPhone(String email, String phone);
}
