package com.m1s.m1sserver.auth.member;

import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;


    public Member findMember(Long memberId){
        Optional<Member> result = memberRepository.findById(memberId);
        if(!result.isPresent())throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        return result.get();
    }



    public void setPassword(Member member, String oldPassword, String newEncodedPassword){
        if(member.getPassword() == newEncodedPassword) throw new CustomException(ErrorCode.DUPLICATE_PASSWORD);
        member.setPassword(newEncodedPassword);
        memberRepository.save(member);
    }

    public void loginInformationCheck(String username, String encodedPassword){
        Member member = memberRepository.findOneByUsername(username);
        String memberPassword = member.getPassword();
        if(memberPassword != authService.encodePassword(encodedPassword))throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }

    public void insertMember(Member member){
        if(memberRepository.existsByUsername(member.getUsername()))
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        member.setPassword(authService.encodePassword(member.getPassword()));
        memberRepository.save(member);
    }

}
