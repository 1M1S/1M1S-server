package com.m1s.m1sserver.api.user.group;

import com.m1s.m1sserver.api.group.Party;
import com.m1s.m1sserver.api.group.PartyRepository;
import com.m1s.m1sserver.api.group.PartyService;
import com.m1s.m1sserver.api.group.member.PartyMember;
import com.m1s.m1sserver.api.group.member.PartyMemberRepository;
import com.m1s.m1sserver.api.group.member.PartyMemberService;
import com.m1s.m1sserver.api.post.Post;
import com.m1s.m1sserver.api.post.PostRepository;
import com.m1s.m1sserver.api.post.PostService;
import com.m1s.m1sserver.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/user/group/{group_id}/post")
public class PartyPostController {
    @Autowired
    private PartyMemberService partyMemberService;
    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PartyService partyService;

    @GetMapping
    public Iterable<Post> getGroupMemberPost(Authentication authentication, @PathVariable Long group_id) {
        Long user_id = authService.getMyId(authentication);
        PartyMember me = partyMemberService.getPartyMember(user_id, group_id);
        Party targetParty = partyService.getParty(group_id);
        return postService.getPosts(me, targetParty);
    }
}
