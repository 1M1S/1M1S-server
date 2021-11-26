package com.m1s.m1sserver.api.user.information;


import com.m1s.m1sserver.auth.member.Member;
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

    public MemberInformation insertMemberInformation(MemberInformation memberInformation){
        if(memberInformationRepository.existsByEmail(memberInformation.getEmail()))
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        if(memberInformationRepository.existsByPhone(memberInformation.getPhone()))
            throw new CustomException(ErrorCode.DUPLICATE_PHONE);
        memberService.insertMember(memberInformation.getMember());
        memberInformationRepository.save(memberInformation);
        return memberInformation;
    }

    public boolean checkOwner(Member member, MemberInformation memberInformation){
        if(member.getId() != memberInformation.getMemberId())throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        return true;
    }

    public MemberInformation editMemberInformation(Member member, MemberInformation oldMemberInformation, MemberInformation newMemberInformation){
        checkOwner(member, oldMemberInformation);
        if(newMemberInformation.getName() != null)
            oldMemberInformation.setName
                    (newMemberInformation.getName());
        if(newMemberInformation.getNickname() != null)
            oldMemberInformation.setNickname
                    (newMemberInformation.getNickname());
        if(newMemberInformation.getGender() != null)
            oldMemberInformation.setGender
                    (newMemberInformation.getGender());
        if(newMemberInformation.getPhone() != null)
            oldMemberInformation.setPhone
                    (newMemberInformation.getPhone());
        if(newMemberInformation.getEmail() != null)
            oldMemberInformation.setEmail
                    (newMemberInformation.getEmail());
        return save(oldMemberInformation);
    }

    public MemberInformation save(MemberInformation memberInformation){
        return memberInformationRepository.save(memberInformation);
    }
    public MemberInformation getMemberInfo(Member member){
        if(!memberInformationRepository.existsByMemberId(member.getId()))throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        return memberInformationRepository.findByMemberId(member.getId());
    }

    public void deleteMemberInformation(Member member){
        MemberInformation targetMemberInformation = getMemberInfo(member);
        memberInformationRepository.deleteById(targetMemberInformation.getId());
    }
}
