package com.m1s.m1sserver.api.group.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Long> {
    PartyMember findByMemberIdAndPartyId(Long user_id, Long group_id);
    Boolean existsByMemberIdAndPartyId(Long user_id, Long group_id);
    Iterable<PartyMember> findAllByPartyId(Long group_id);
    void deleteAllByPartyId(Long group_id);
    void deleteAllByMemberId(Long user_id);
}
