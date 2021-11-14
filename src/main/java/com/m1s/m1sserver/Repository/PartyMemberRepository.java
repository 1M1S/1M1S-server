package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Long> {
}
