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
        return save(PartyMember.builder()
                .party(party)
                .member(member)
                .authority(authority)
                .build());
    }

    public PartyMember getPartyMember(Long user_id, Long group_id){
        if(!partyMemberRepository.existsByMemberIdAndPartyId(user_id, group_id))
            throw new CustomException(ErrorCode.PARTICIPANT_NOT_FOUND);
        return partyMemberRepository.findByMemberIdAndPartyId(user_id, group_id);
    }

    public Iterable<PartyMember> getPartyMembers(Long group_id){
        return partyMemberRepository.findAllByPartyId(group_id);
    }

    public void deletePartyMembers(Party party){
        partyMemberRepository.deleteAllByPartyId(party.getId());
    }

    public void deletePartyMembers(Long group_id){
        partyMemberRepository.deleteAllByPartyId(group_id);
    }

    public void deletePartyMember(PartyMember partyMember){
        if(partyMember.getAuthority().equals("그룹장"))throw new CustomException(ErrorCode.LEADER_CANT_LEAVE);
        deletePartyMember(partyMember.getId());
    }

    public void deletePartyMember(Long party_member_id){
        if(!partyMemberRepository.existsById(party_member_id))throw new CustomException(ErrorCode.PARTICIPANT_NOT_FOUND);
        partyMemberRepository.deleteById(party_member_id);
    }

    public void deletePartyMembers(Member member){
        partyMemberRepository.deleteAllByMemberId(member.getId());
    }
    public PartyMember save(PartyMember partyMember){
        return partyMemberRepository.save(partyMember);
    }
}
