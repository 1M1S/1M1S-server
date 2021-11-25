package com.m1s.m1sserver.api.group.member;


import com.m1s.m1sserver.api.group.Party;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyMemberService {
    @Autowired
    private PartyMemberRepository partyMemberRepository;

    public PartyMember createPartyMember(Member member, Party party, String authority){
        return PartyMember.builder()
                .party(party)
                .member(member)
                .authority(authority)
                .build();
    }

    public PartyMember getPartyMember(Long user_id, Long group_id){
        if(!partyMemberRepository.existsByMemberIdAndPartyId(user_id, group_id))
            throw new CustomException(ErrorCode.PARTICIPANT_NOT_FOUND);
        return partyMemberRepository.findByMemberIdAndPartyId(user_id, group_id);
    }

    public PartyMember save(PartyMember partyMember){
        return partyMemberRepository.save(partyMember);
    }
}
