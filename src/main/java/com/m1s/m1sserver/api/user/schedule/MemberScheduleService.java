package com.m1s.m1sserver.api.user.schedule;


import com.m1s.m1sserver.api.admin.enviroment.EnvironmentService;
import com.m1s.m1sserver.api.ranking.Ranking;
import com.m1s.m1sserver.api.ranking.RankingService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



@Service
public class MemberScheduleService {
    @Autowired
    private MemberScheduleRepository memberScheduleRepository;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private EnvironmentService environmentService;

    public boolean checkOwner(Member member, MemberSchedule memberSchedule){
        if(member.getId() != memberSchedule.getMemberId())throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        return true;
    }

    public MemberSchedule getMemberSchedule(Long member_schedule_id){
        if(!memberScheduleRepository.existsById(member_schedule_id))throw new CustomException(ErrorCode.MEMBER_SCHEDULE_NOT_FOUND);
        return memberScheduleRepository.findById(member_schedule_id).get();
    }

    public Iterable<MemberSchedule> getMemberSchedules(Member member){
        return memberScheduleRepository.findAllByMemberId(member.getId(), Sort.by("startTime"));
    }

    public MemberSchedule createMemberSchedule(Member member, MemberSchedule memberSchedule){
        checkOwner(member, memberSchedule);
        memberSchedule.setMember(member);
        memberSchedule.setFinish(false);
        return save(memberSchedule);
    }

    public MemberSchedule editMemberSchedule(Member member, MemberSchedule oldMemberSchedule, MemberSchedule newMemberSchedule) {
        checkOwner(member, oldMemberSchedule);

        boolean beforeFinish = oldMemberSchedule.getFinish();
        if (newMemberSchedule.getFinish() != null)
            oldMemberSchedule.setFinish(newMemberSchedule.getFinish());
        if (newMemberSchedule.getStartTime() != null)
            oldMemberSchedule.setStartTime(newMemberSchedule.getStartTime());
        if (newMemberSchedule.getEndTime() != null)
            oldMemberSchedule.setEndTime(newMemberSchedule.getEndTime());
        if (newMemberSchedule.getInterest() != null)
            oldMemberSchedule.setInterest(newMemberSchedule.getInterest());
        if (newMemberSchedule.getContent() != null)
            oldMemberSchedule.setContent(newMemberSchedule.getContent());
        if (oldMemberSchedule.getFinish() && oldMemberSchedule.getFinish() != beforeFinish) {
            Ranking targetRanking = rankingService.getRanking(member, oldMemberSchedule.getInterest());
            rankingService.editScore(targetRanking, oldMemberSchedule);
        }
        return save(oldMemberSchedule);
    }


    public void deleteMemberSchedule(Member member, MemberSchedule memberSchedule){
        checkOwner(member, memberSchedule);
        if(!memberScheduleRepository.existsById(memberSchedule.getId()))throw new CustomException(ErrorCode.MEMBER_SCHEDULE_NOT_FOUND);
        Ranking targetRanking = rankingService.getRanking(member, memberSchedule.getInterest());
        if(memberSchedule.getFinish())rankingService.deleteScore(targetRanking, memberSchedule);
    }

    public MemberSchedule save(MemberSchedule memberSchedule){
        return memberScheduleRepository.save(memberSchedule);
    }

}
