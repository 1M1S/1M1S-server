package com.m1s.m1sserver.auth.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    Member findOneByUsername(String username);
}
