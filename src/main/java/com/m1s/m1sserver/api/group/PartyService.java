package com.m1s.m1sserver.api.group;


import com.m1s.m1sserver.api.group.member.PartyMember;
import com.m1s.m1sserver.api.group.member.PartyMemberService;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class PartyService {


    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyMemberService partyMemberService;
    public Iterable<Party> getParties(Long interest_id){
        if(interest_id == null)return getParties();
        return partyRepository.findAllByInterestIdAndRecruit
                (interest_id, true);
    }
    public Iterable<Party> getParties(){
        return partyRepository.findAllByRecruit(true);
    }

    public Party addParty(Party party){
        return partyRepository.save(party);
    }

    public Party getParty(Long party_id){
        if(!partyRepository.existsById(party_id))throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
        return partyRepository.findById(party_id).get();
    }

    public Party editParty(PartyMember me, Party targetParty, Party inputParty){
        if(checkOwner(me, targetParty))
        if(inputParty.getRecruit() != null)
            targetParty.setRecruit(inputParty.getRecruit());
        if(inputParty.getGoal() != null)
            targetParty.setGoal(inputParty.getGoal());
        if(inputParty.getInterest() != null)
            targetParty.setInterest(inputParty.getInterest());
        if(inputParty.getName() != null)
            targetParty.setName(inputParty.getName());
        if(inputParty.getMaximumNumberOfPeople() != null)
            targetParty.setMaximumNumberOfPeople(inputParty.getMaximumNumberOfPeople());
        return partyRepository.save(targetParty);
    }

    public boolean checkOwner(PartyMember partyMember, Party party){
        if(partyMember.getPartyId() != party.getId() || !partyMember.getAuthority().equals("그룹장"))throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        return true;
    }
    public boolean checkParticipant(PartyMember partyMember, Party party){
        if(partyMember.getPartyId() != party.getId() || partyMember.getAuthority().equals("승인대기"))throw new CustomException(ErrorCode.PARTICIPANT_NOT_FOUND);
        return true;
    }
    public void deleteParty(PartyMember requester, Party party){
        if(checkOwner(requester, party))deleteParty(party.getId());
    }



    public void deleteParty(Long group_id){
        if(!partyRepository.existsById(group_id))throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
        partyMemberService.deletePartyMembers(group_id);
        partyRepository.deleteById(group_id);
    }
    public Party save(Party targetParty) {
        return partyRepository.save(targetParty);
    }
}
