package com.m1s.m1sserver.api.user.information;


import com.m1s.m1sserver.auth.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberInformationService {
    @Autowired
    MemberInformationRepository memberInformationRepository;

    @Autowired
    MemberService memberService;
    public void insertMemberInformaion(MemberInformation memberInformation){
        if(memberInformationRepository.existsByEmailOrPhone(memberInformation.getEmail(), memberInformation.getPhone()));
        memberService.insertMember(memberInformation.getMember());
        memberInformationRepository.save(memberInformation);
    }
}
