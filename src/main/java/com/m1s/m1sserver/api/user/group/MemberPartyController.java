package com.m1s.m1sserver.api.user.group;

import com.m1s.m1sserver.api.group.Party;
import com.m1s.m1sserver.api.group.PartyRepository;
import com.m1s.m1sserver.api.group.member.PartyMember;
import com.m1s.m1sserver.api.group.member.PartyMemberRepository;
import com.m1s.m1sserver.auth.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/{user_id}/group")
public class MemberPartyController {
    @Autowired
    PartyRepository partyRepository;
    @Autowired
    PartyMemberRepository partyMemberRepository;
    @Autowired
    MemberRepository memberRepository;

    @PostMapping
    public Party addParty(@PathVariable Long user_id, @RequestBody Party p) {
        partyRepository.save(p);
        PartyMember m = new PartyMember();
        m.setParty(p);
        m.setAuthority("그룹장");
        m.setMember(memberRepository.findById(user_id).get());
        partyMemberRepository.save(m);
        return p;
    }

    @GetMapping
    public Iterable<Party> getParty(@PathVariable Long user_id) {
        return partyRepository.findAllByUserId(user_id);
    }

    @PostMapping("/{group_id}")
    public Party requestParty(@PathVariable Long user_id, @PathVariable Long group_id) {
        Party p = partyRepository.findById(group_id).get();
        PartyMember m = new PartyMember();
        m.setParty(p);
        m.setAuthority("승인대기");
        m.setMember(memberRepository.findById(user_id).get());
        partyMemberRepository.save(m);
        return p;
    }

    @GetMapping("/{group_id}")
    public Party getParty(@PathVariable Long user_id, @PathVariable Long group_id) {
        return partyRepository.findById(group_id).get();
    }

    @PutMapping("/{group_id}")
    public ResponseEntity<Party> editParty(@PathVariable Long user_id, @PathVariable Long group_id, @RequestBody Party p) {
        PartyMember pm = partyMemberRepository.findByMemberIdAndPartyId(user_id, group_id);
        if(!pm.getAuthority().equals("그룹장")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Party edit = partyRepository.findById(group_id).get();
        if(p.getRecruit() != null) edit.setRecruit(p.getRecruit());
        if(p.getGoal() != null) edit.setGoal(p.getGoal());
        if(p.getInterest() != null) edit.setInterest(p.getInterest());
        if(p.getName() != null) edit.setName(p.getName());
        if(p.getMaximumNumberOfPeople() != null) edit.setMaximumNumberOfPeople(p.getMaximumNumberOfPeople());

        partyRepository.save(edit);
        return new ResponseEntity<>(edit, HttpStatus.OK);
    }

    @DeleteMapping("/{group_id}")
    public Party delParty(@PathVariable Long user_id, @PathVariable Long group_id) {
        PartyMember pm = partyMemberRepository.findByMemberIdAndPartyId(user_id, group_id);
        Party p = partyRepository.findById(group_id).get();
        if(pm.getAuthority().equals("그룹장")) {
            Iterable<PartyMember> PM = partyMemberRepository.findAllByPartyId(group_id);
            for(PartyMember Pm : PM) partyMemberRepository.deleteById(Pm.getId());
            partyRepository.deleteById(group_id);
        }
        else {
            partyMemberRepository.deleteById(partyMemberRepository.findByMemberIdAndPartyId(user_id, group_id).getId());
        }
        return p;
    }
}
