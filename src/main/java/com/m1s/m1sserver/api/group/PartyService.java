package com.m1s.m1sserver.api.group;


import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class PartyService {


    @Autowired
    private PartyRepository partyRepository;

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

    public Party editParty(Party targetParty, Party inputParty){
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

    public Party save(Party targetParty) {
        return partyRepository.save(targetParty);
    }
}
