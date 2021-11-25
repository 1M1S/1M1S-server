package com.m1s.m1sserver.api.user.counsel_result;


import com.m1s.m1sserver.api.counsel_solution.CounselSolution;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberCounselResultService {

    @Autowired
    private MemberCounselResultRepository memberCounselResultRepository;

    @Autowired
    private AuthService authService;

    public Iterable<MemberCounselResult> getCounselResults(Long member_id){
        return memberCounselResultRepository.findAllByMemberIdOrderByCounselTime(member_id);
    }

    public MemberCounselResult getCounselResult(Long member_counsel_result_id){
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

    public MemberCounselResult createMemberCounselResult(Member member, CounselSolution counselSolution){
        return MemberCounselResult.builder()
                .member(member)
                .counsel_solution(counselSolution)
                .counsel_time(LocalDateTime.now())
                .build();
    }

    public Boolean checkOwner(Long user_id, MemberCounselResult memberCounselResult){
        return user_id.equals(memberCounselResult.getMemberId());
    }
    public MemberCounselResult deleteMemberCounselResult(Authentication authentication,  Long member_counsel_result_id){
        MemberCounselResult foundMemberCounselResult =
                getCounselResult(member_counsel_result_id);
        if(!checkOwner(authService.getMyId(authentication), foundMemberCounselResult))
            throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        if(!memberCounselResultRepository.existsById(member_counsel_result_id))throw new CustomException(ErrorCode.MEMBER_COUNSEL_RESULT_NOT_FOUND);
        memberCounselResultRepository.deleteById(member_counsel_result_id);
        return foundMemberCounselResult;
    }
}
