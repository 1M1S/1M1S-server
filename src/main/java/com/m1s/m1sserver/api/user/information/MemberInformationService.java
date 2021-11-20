package com.m1s.m1sserver.api.user.information;


import com.m1s.m1sserver.auth.member.MemberService;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberInformationService {
    @Autowired
    MemberInformationRepository memberInformationRepository;

    @Autowired
    MemberService memberService;
    public MemberInformation insertMemberInformaion(MemberInformation memberInformation){
        if(memberInformationRepository.existsByEmail(memberInformation.getEmail()))
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        if(memberInformationRepository.existsByPhone(memberInformation.getPhone()))
            throw new CustomException(ErrorCode.DUPLICATE_PHONE);
        memberService.insertMember(memberInformation.getMember());
        memberInformationRepository.save(memberInformation);
        return memberInformation;
    }
}
