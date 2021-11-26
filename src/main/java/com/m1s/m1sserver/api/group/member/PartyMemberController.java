package com.m1s.m1sserver.api.group.member;

import com.m1s.m1sserver.api.group.member.PartyMember;
import com.m1s.m1sserver.api.group.member.PartyMemberRepository;
import com.m1s.m1sserver.api.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/group/{group_id}/member")
public class PartyMemberController {
    @Autowired
    PartyMemberRepository partyMemberRepository;

    @GetMapping
    public ResponseEntity<Iterable<PartyMember>> getGroupMember(@PathVariable Long user_id, @PathVariable Long group_id) {
        PartyMember p = partyMemberRepository.findByMemberIdAndPartyId(user_id, group_id);
        if(p == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(p.getAuthority().equals("승인대기")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(partyMemberRepository.findAllByPartyId(group_id), HttpStatus.OK);
    }

    @PutMapping("/{member_id}")
    public ResponseEntity<PartyMember> editGroupMember(@PathVariable Long user_id, @PathVariable Long group_id, @PathVariable Long member_id, @RequestParam String authority) {
        PartyMember p = partyMemberRepository.findByMemberIdAndPartyId(user_id, group_id);
        if(!p.getAuthority().equals("그룹장")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        PartyMember m = partyMemberRepository.findById(member_id).get();
        m.setAuthority(authority);
        partyMemberRepository.save(m);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @DeleteMapping("/{member_id}")
    public ResponseEntity<PartyMember> delGroupMember(@PathVariable Long user_id, @PathVariable Long group_id, @PathVariable Long member_id) {
        PartyMember p = partyMemberRepository.findByMemberIdAndPartyId(user_id, group_id);
        if(!p.getAuthority().equals("그룹장")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        PartyMember m = partyMemberRepository.findById(member_id).get();
        partyMemberRepository.deleteById(member_id);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }
}
