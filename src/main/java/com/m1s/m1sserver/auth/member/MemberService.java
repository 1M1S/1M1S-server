package com.m1s.m1sserver.auth.member;

import com.m1s.m1sserver.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;


    public Member getMe(Long memberId){
        Optional<Member> result = memberRepository.findById(memberId);
        return result.get();
    }



    public void setPassword(Long memberId, String oldPassword, String newPassword){
        Optional<Member> member = memberRepository.findById(memberId);
    }

    public boolean loginInformationCheck(String username, String encodedPassword){
        Member member = memberRepository.findOneByUsername(username);
        if(member == null)return false;
        String memberPassword = member.getPassword();
        if(memberPassword != authService.encodePassword(encodedPassword))return false;
        return true;
    }

    public void insertMember(Member member){
        if(memberRepository.existsByUsername(member.getUsername()));
        member.setPassword(authService.encodePassword(member.getPassword()));
        memberRepository.save(member);
    }

}
