package com.m1s.m1sserver.api.user.schedule;


import com.m1s.m1sserver.api.admin.enviroment.EnvironmentService;
import com.m1s.m1sserver.api.interest.Interest;
import com.m1s.m1sserver.api.ranking.Ranking;
import com.m1s.m1sserver.api.ranking.RankingService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class MemberScheduleService {
    @Autowired
    private MemberScheduleRepository memberScheduleRepository;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private EnvironmentService environmentService;

    public boolean checkOwner(Member member, MemberSchedule memberSchedule){
        if(member.getId() != memberSchedule.getMember().getId())throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        return true;
    }

    public MemberSchedule getMemberSchedule(Long member_schedule_id){
        if(!memberScheduleRepository.existsById(member_schedule_id))throw new CustomException(ErrorCode.MEMBER_SCHEDULE_NOT_FOUND);
        return memberScheduleRepository.findById(member_schedule_id).get();
    }

    public Iterable<MemberSchedule> getMemberSchedules(Member member){
        return memberScheduleRepository.findAllByMember(member, Sort.by("startTime"));
    }
    public Iterable<MemberSchedule> getMemberSchedules(Member member, String search_time){
        LocalDateTime t = LocalDate.parse(search_time, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        return memberScheduleRepository.findAllByMemberAndStartTime(member, t.getYear(),
                t.getMonthValue(), t.getDayOfMonth());
    }
    public MemberSchedule createMemberSchedule(Member member, MemberSchedule memberSchedule){
        memberSchedule.setMember(member);
        memberSchedule.setFinish(false);
        return save(memberSchedule);
    }

    public MemberSchedule editMemberSchedule(Member member, MemberSchedule memberSchedule, MemberSchedule newMemberSchedule) {
        checkOwner(member, memberSchedule);

        MemberSchedule oldMemberSchedule = new MemberSchedule(memberSchedule);

        if (newMemberSchedule.getFinish() != null) memberSchedule.setFinish(newMemberSchedule.getFinish());
        if (newMemberSchedule.getStartTime() != null) memberSchedule.setStartTime(newMemberSchedule.getStartTime());
        if (newMemberSchedule.getEndTime() != null) memberSchedule.setEndTime(newMemberSchedule.getEndTime());
        if (newMemberSchedule.getInterest() != null) memberSchedule.setInterest(newMemberSchedule.getInterest());
        if (newMemberSchedule.getContent() != null) memberSchedule.setContent(newMemberSchedule.getContent());

        Ranking targetRanking = rankingService.getRanking(member, memberSchedule.getInterest());
        Ranking oldRanking = rankingService.getRanking(member, oldMemberSchedule.getInterest());

        // ????????????????????? ????????? ??????
        if(oldMemberSchedule.getFinish() != memberSchedule.getFinish()) {
            // false -> true : ?????? ??????
            if(memberSchedule.getFinish()) rankingService.addScore(targetRanking, memberSchedule);
            // true -> false : ?????? ??????
            // ???????????? ????????? ????????? ??? ???????????? oldMemberSchedule ???????????? ?????? ??????.
            else rankingService.deleteScore(oldRanking, oldMemberSchedule);
        }
        // ????????????????????? true??? ????????? ??????
        else if(memberSchedule.getFinish()){
            // ????????????, ??????????????? ????????? ?????? : ?????? ??????
            // ???????????? ????????? ?????? : ?????? ??????
            if(oldMemberSchedule.getStartTime() != memberSchedule.getStartTime() || oldMemberSchedule.getEndTime() != memberSchedule.getEndTime() || oldMemberSchedule.getInterest() != memberSchedule.getInterest()) {
                rankingService.deleteScore(oldRanking, oldMemberSchedule);
                rankingService.addScore(targetRanking, memberSchedule);
            }
        }
        return save(memberSchedule);
    }

    public void deleteMemberSchedule(Member member, MemberSchedule memberSchedule){
        checkOwner(member, memberSchedule);
        if(!memberScheduleRepository.existsById(memberSchedule.getId()))throw new CustomException(ErrorCode.MEMBER_SCHEDULE_NOT_FOUND);
        // ?????? ?????? ????????? true??? ?????? ???????????? ?????? ??????
        if(memberSchedule.getFinish()) {
            Ranking targetRanking = rankingService.getRanking(member, memberSchedule.getInterest());
            rankingService.deleteScore(targetRanking, memberSchedule);
        }
        memberScheduleRepository.deleteById(memberSchedule.getId());
    }

    public void deleteMemberSchedules(Member member){
        memberScheduleRepository.deleteAllByMember(member);
    }

    public MemberSchedule save(MemberSchedule memberSchedule){
        return memberScheduleRepository.save(memberSchedule);
    }

}
