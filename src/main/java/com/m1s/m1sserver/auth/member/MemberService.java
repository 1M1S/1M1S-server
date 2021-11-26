package com.m1s.m1sserver.auth.member;

import com.m1s.m1sserver.api.group.member.PartyMemberService;
import com.m1s.m1sserver.api.ranking.Ranking;
import com.m1s.m1sserver.api.ranking.RankingService;
import com.m1s.m1sserver.api.register_survey.RegisterSurveyService;
import com.m1s.m1sserver.api.user.counsel_result.MemberCounselResultService;
import com.m1s.m1sserver.api.user.curriculum.MemberCurriculumService;
import com.m1s.m1sserver.api.user.information.MemberInformationService;
import com.m1s.m1sserver.api.user.interest.MemberInterestService;
import com.m1s.m1sserver.api.user.schedule.MemberSchedule;
import com.m1s.m1sserver.api.user.schedule.MemberScheduleService;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder passwordEncoder;




    public Member getMember(Long memberId){
        if(!memberRepository.existsById(memberId))throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        return memberRepository.findById(memberId).get();
    }



    public void setPassword(Member member, String oldPassword, String newPassword){
        //TODO refcell22 - 여기도 바꿔
        if(passwordEncoder.matches(newPassword,oldPassword)) throw new CustomException(ErrorCode.DUPLICATE_PASSWORD);
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    //refcell22 - 여기서 받은 비밀번호는 encoded가 아니라 평문 패스워드다..
    public Member loginInformationCheck(String username, String inputPassword){
        Member member = getMember(username);
        String memberPassword = member.getPassword();
        //refcell22 - Bcrypt 검사는 문자열 비교로 하면 안됨, 반드시 Bcrypt의 비교함수를 사용할것
        if(!passwordEncoder.matches(inputPassword,memberPassword)) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        return member;
    }
    public Member getMember(String username){
        if(!memberRepository.existsByUsername(username))throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        return memberRepository.findOneByUsername(username);
    }
    public void insertMember(Member member){
        if(memberRepository.existsByUsername(member.getUsername()))
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        member.setPassword(authService.encodePassword(member.getPassword()));
        memberRepository.save(member);
    }
    public void deleteMember(Member member){
        memberRepository.deleteById(member.getId());

    }
}
