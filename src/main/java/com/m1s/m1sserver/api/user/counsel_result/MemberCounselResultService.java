package com.m1s.m1sserver.api.user.counsel_result;


import com.m1s.m1sserver.api.counsel_solution.CounselSolution;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberCounselResultService {

    @Autowired
    private MemberCounselResultRepository memberCounselResultRepository;

    @Autowired
    private AuthService authService;

    public Iterable<MemberCounselResult> getMemberCounselResults(Member member){
        return memberCounselResultRepository.findAllByMember(member, Sort.by(Sort.Direction.DESC, "counselTime"));
    }

    public MemberCounselResult getMemberCounselResult(Member member){
        if(!memberCounselResultRepository.existsById(member.getId()))throw new CustomException(ErrorCode.MEMBER_COUNSEL_RESULT_NOT_FOUND);
        return memberCounselResultRepository.findByMember(member);
    }

    public MemberCounselResult getMemberCounselResult(Long member_counsel_result_id){
        if(!memberCounselResultRepository.existsById(member_counsel_result_id))throw new CustomException(ErrorCode.MEMBER_COUNSEL_RESULT_NOT_FOUND);
        return memberCounselResultRepository.findById(member_counsel_result_id).get();
    }

    public void save(MemberCounselResult memberCounselResult){
        try{
            memberCounselResultRepository.save(memberCounselResult);
        }catch (Exception e){
            throw new CustomException(ErrorCode.SAVE_FAILED);
        }

    }

    public MemberCounselResult createMemberCounselResult(Member member, String counselSolution){
        return MemberCounselResult.builder()
                .member(member)
                .counselSolution(counselSolution)
                .counselTime(LocalDateTime.now())
                .build();
    }

    public Boolean checkOwner(Member member, MemberCounselResult memberCounselResult){
        if(member.getId() != memberCounselResult.getMemberId())
            throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        return true;
    }
    public void deleteMemberCounselResult(Member member, MemberCounselResult memberCounselResult){
        checkOwner(member,memberCounselResult );
        if(!memberCounselResultRepository.existsById(memberCounselResult.getId()))throw new CustomException(ErrorCode.MEMBER_COUNSEL_RESULT_NOT_FOUND);
        memberCounselResultRepository.deleteById(memberCounselResult.getId());

    }

    public void deleteMemberCounselResults(Member member){
        memberCounselResultRepository.deleteAllByMember(member);
    }
}
