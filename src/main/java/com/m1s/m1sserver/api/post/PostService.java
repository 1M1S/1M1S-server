package com.m1s.m1sserver.api.post;


import com.m1s.m1sserver.api.group.Party;
import com.m1s.m1sserver.api.group.PartyService;
import com.m1s.m1sserver.api.group.member.PartyMember;
import com.m1s.m1sserver.api.group.member.PartyMemberService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PartyService partyService;

    public Post createPost(Member member, Post newPost){
        newPost.setMember(member);
        newPost.setWriting_date(LocalDateTime.now());
        return save(newPost);
    }

    public Post editPost(Member member, Post oldPost, Post newPost){
        checkOwner(member, oldPost);
        if(newPost.getInterest() != null) oldPost.setInterest(newPost.getInterest());
        if(newPost.getTitle() != null) oldPost.setTitle(newPost.getTitle());
        if(newPost.getContent() != null) oldPost.setContent(newPost.getContent());
        return save(oldPost);
    }
    public Iterable<Post> getPosts(Long interest_id) {
        if(interest_id == null) return getPosts();
        return postRepository.findAllByInterestId(interest_id, Sort.by(Sort.Direction.DESC, "writingDate"));
    }

    public Iterable<Post> getPosts(PartyMember partyMember, Party party){
        partyService.checkParticipant(partyMember,party);
        return getPosts(party);
    }

    public  Iterable<Post> getPosts(Member member){
        return postRepository.findByMemberId(member.getId(),Sort.by(Sort.Direction.DESC, "writingDate"));
    }

    public Iterable<Post> getPosts(Party party){
        return postRepository.findAllByPartyId(party.getId());
    }

    public Iterable<Post> getPosts(){
        return postRepository.findAll(Sort.by("writingDate"));
    }

    public boolean checkOwner(Member member, Post post){
        if(member.getId() != post.getMemberId())throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        return true;
    }

    public Post save(Post post){
        return postRepository.save(post);
    }

    public Post getPost(Long post_id) {
        if(!postRepository.existsById(post_id))throw new CustomException(ErrorCode.POST_NOT_FOUND);
        return postRepository.findById(post_id).get();
    }

    public void deletePost(Member me, Post post){
        checkOwner(me, post);
        postRepository.deleteById(post.getId());
    }
}
