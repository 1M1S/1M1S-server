package com.m1s.m1sserver.api.user.schedule;

import com.m1s.m1sserver.api.admin.enviroment.EnvironmentRepository;
import com.m1s.m1sserver.api.ranking.Ranking;
import com.m1s.m1sserver.api.ranking.RankingRepository;
import com.m1s.m1sserver.auth.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/{user_id}/schedule")
public class MemberScheduleController {
    @Autowired
    private MemberScheduleRepository memberScheduleRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private EnvironmentRepository environmentRepository;

    @PostMapping
    public MemberSchedule addMemberSchedule(@PathVariable Long user_id, @RequestBody MemberSchedule m) {
        m.setMember(memberRepository.findById(user_id).get());
        m.setFinish(false);
        memberScheduleRepository.save(m);
        return m;
    }

    @GetMapping
    public Iterable<MemberSchedule> getMemberSchedule(@PathVariable Long user_id) {
        return memberScheduleRepository.findAllByMemberId(user_id, Sort.by("startTime"));
    }

    @PutMapping("/{member_schedule_id}")
    public MemberSchedule editMemberSchedule(@PathVariable Long user_id, @PathVariable Long member_schedule_id, @RequestBody MemberSchedule m) {
        MemberSchedule edit = memberScheduleRepository.findById(member_schedule_id).get();
        Ranking r = rankingRepository.findByMemberIdAndInterestId(user_id, edit.getInterest().getId());
        final String score_per_minute = environmentRepository.findByName("score_per_minute").getValue();

        if (m.getFinish() != null && !edit.getFinish().equals(m.getFinish())) {
            edit.setFinish(m.getFinish());
            // 일정 완료 여부에 따라 점수 부여/제거
            if (edit.getFinish().equals(true))
                r.setScore(r.getScore() + edit.calScore(score_per_minute));
            else
                r.setScore(r.getScore() - edit.calScore(score_per_minute));
        }
        if (m.getStartTime() != null && !edit.getStartTime().equals(m.getStartTime())) {
            // 일정 완료 여부가 true일 경우 점수 수정
            if(edit.getFinish().equals(true)) {
                r.setScore(r.getScore() - edit.calScore(score_per_minute));
                edit.setStartTime(m.getStartTime());
                r.setScore(r.getScore() + edit.calScore(score_per_minute));
            }
            else edit.setStartTime(m.getStartTime());
        }
        if (m.getEndTime() != null && !edit.getEndTime().equals(m.getEndTime())) {
            // 일정 완료 여부가 true일 경우 점수 수정
            if(edit.getFinish().equals(true)){
                r.setScore(r.getScore() - edit.calScore(score_per_minute));
                edit.setEndTime(m.getEndTime());
                r.setScore(r.getScore() + edit.calScore(score_per_minute));
            }
            else edit.setEndTime(m.getEndTime());
        }
        if (m.getInterest() != null && !edit.getInterest().equals(m.getInterest())) {
            // 일정 완료 여부가 true일 경우 점수 이동
            if(edit.getFinish().equals(true)) {
                r.setScore(r.getScore() - edit.calScore(score_per_minute));
                r = rankingRepository.findByMemberIdAndInterestId(user_id, m.getInterest().getId());
                r.setScore(r.getScore() + edit.calScore(score_per_minute));
            }
            edit.setInterest(m.getInterest());
        }
        if (m.getContent() != null && !edit.getContent().equals(m.getContent())) edit.setContent(m.getContent());

        memberScheduleRepository.save(edit);
        return edit;
    }

    @DeleteMapping("/{member_schedule_id}")
    public MemberSchedule delMemberSchedule(@PathVariable Long user_id, @PathVariable Long member_schedule_id) {
        final String score_per_minute = environmentRepository.findByName("score_per_minute").getValue();
        MemberSchedule m = memberScheduleRepository.findById(member_schedule_id).get();
        if (m.getFinish().equals(true)) {
            Ranking r = rankingRepository.findByMemberIdAndInterestId(user_id, m.getInterest().getId());
            r.setScore(r.getScore() - m.calScore(score_per_minute));
        }
        memberScheduleRepository.deleteById(member_schedule_id);
        return m;
    }
}
