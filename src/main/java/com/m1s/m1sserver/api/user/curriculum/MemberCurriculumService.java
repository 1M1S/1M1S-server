package com.m1s.m1sserver.api.user.curriculum;


import com.m1s.m1sserver.api.curriculum.Curriculum;
import com.m1s.m1sserver.api.user.counsel_result.MemberCounselResult;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class MemberCurriculumService {
    @Autowired
    private MemberCurriculumRepository memberCurriculumRepository;

    @Autowired
    private AuthService authService;

    public Boolean checkOwner(Long user_id, MemberCurriculum memberCurriculum){
        return user_id.equals(memberCurriculum.getMemberId());
    }

    public MemberCurriculum createMemberCurriculum(Member member, Curriculum curriculum){
        return MemberCurriculum.builder()
                .member(member)
                .curriculum(curriculum)
                .build();
    }

    public Iterable<MemberCurriculum> getMemberCurriculums(Long user_id){
        return memberCurriculumRepository.findAllByMemberId(user_id);
    }

    public MemberCurriculum getMemberCurriculum(Long member_curriculum_id){
        if(!memberCurriculumRepository.existsById(member_curriculum_id))
            throw new CustomException(ErrorCode.MEMBER_CURRICULUM_NOT_FOUND);
        return memberCurriculumRepository.findById(member_curriculum_id).get();
    }

    public MemberCurriculum deleteMemberCurriculum(Authentication authentication, Long member_curriculum_id){

        MemberCurriculum foundMemberCurriculum = getMemberCurriculum(member_curriculum_id);
        if(!checkOwner(authService.getMyId(authentication), foundMemberCurriculum))
            throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        memberCurriculumRepository.delete(foundMemberCurriculum);
        return foundMemberCurriculum;
    }

    public void deleteMemberCurriculums(Member member){
        memberCurriculumRepository.deleteAllByMemberId(member.getId());
    }
    public MemberCurriculum save(MemberCurriculum memberCurriculum){
        return memberCurriculumRepository.save(memberCurriculum);
    }
}
