package com.m1s.m1sserver.api.user.group;

import com.m1s.m1sserver.api.group.Party;
import com.m1s.m1sserver.api.group.PartyRepository;
import com.m1s.m1sserver.api.group.PartyService;
import com.m1s.m1sserver.api.group.member.PartyMember;
import com.m1s.m1sserver.api.group.member.PartyMemberRepository;
import com.m1s.m1sserver.api.group.member.PartyMemberService;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberRepository;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/group")
public class MemberPartyController {
    @Autowired
    private PartyService partyService;
    @Autowired
    private PartyMemberService partyMemberService;
    @Autowired
    private AuthService authService;

    @PostMapping
    public Party addParty(Authentication authentication, @RequestBody Party party) {
        Member me = authService.getMe(authentication);
        party = partyService.addParty(party);
        partyMemberService.createPartyMember(me, party, "그룹장");
        return party;
    }

    @GetMapping
    public Iterable<Party> getParty(Authentication authentication) {
        Long user_id = authService.getMyId(authentication);
        return partyService.getParties(user_id);
    }

    @PostMapping("/{group_id}")
    public Party requestParty(Authentication authentication, @PathVariable Long group_id) {
        Party targetParty = partyService.getParty(group_id);
        Member me = authService.getMe(authentication);
        partyMemberService.createPartyMember(me, targetParty, "승인대기");
        return targetParty;
    }

    @GetMapping("/{group_id}")
    public Party getParty(@PathVariable Long group_id) {

        return partyService.getParty(group_id);
    }

    @PutMapping("/{group_id}")
    public Party editParty(Authentication authentication, @PathVariable Long group_id, @RequestBody Party inputParty) {
        Member me = authService.getMe(authentication);
        Party party = partyService.getParty(group_id);
        PartyMember partyMemberMe = partyMemberService.getPartyMember(me, party);
        Party targetParty = partyService.getParty(group_id);
        targetParty = partyService.editParty(partyMemberMe, targetParty, inputParty);
        return targetParty;
    }

    @DeleteMapping("/{group_id}")
    public Party deleteParty(Authentication authentication, @PathVariable Long group_id) {
        Member me = authService.getMe(authentication);
        Party party = partyService.getParty(group_id);
        PartyMember partyMemberMe = partyMemberService.getPartyMember(me, party);
        Party targetParty = partyService.getParty(group_id);
        partyService.deleteParty(partyMemberMe, targetParty);
        return targetParty;
    }


    @DeleteMapping("/{group_id}/leave")
    public Party leaveParty(Authentication authentication, @PathVariable Long group_id){
        Member me = authService.getMe(authentication);
        Party party = partyService.getParty(group_id);
        PartyMember partyMemberMe = partyMemberService.getPartyMember(me, party);
        Party targetParty = partyService.getParty(group_id);
        partyMemberService.deletePartyMember(partyMemberMe.getId());
        return targetParty;
    }
}
