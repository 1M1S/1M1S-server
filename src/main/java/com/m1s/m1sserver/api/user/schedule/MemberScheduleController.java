package com.m1s.m1sserver.api.user.schedule;

import com.m1s.m1sserver.api.admin.enviroment.EnvironmentRepository;
import com.m1s.m1sserver.api.interest.Interest;
import com.m1s.m1sserver.api.ranking.Ranking;
import com.m1s.m1sserver.api.ranking.RankingRepository;
import com.m1s.m1sserver.api.ranking.RankingService;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/user/schedule")
public class MemberScheduleController {
    @Autowired
    private MemberScheduleService memberScheduleService;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private AuthService authService;



    @PostMapping
    public MemberSchedule addMemberSchedule(Authentication authentication, @RequestBody MemberSchedule memberSchedule) {
        Member me = authService.getMe(authentication);
        return memberScheduleService.createMemberSchedule(me, memberSchedule);
    }

    @GetMapping
    public Iterable<MemberSchedule> getMemberSchedules(Authentication authentication, @RequestParam String search_time) {
        Member me = authService.getMe(authentication);
        return memberScheduleService.getMemberSchedules(me, search_time);
    }

    @PutMapping("/{member_schedule_id}")
    public MemberSchedule editMemberSchedule(Authentication authentication, @PathVariable Long member_schedule_id, @RequestBody MemberSchedule newMemberSchedule) {
        Member me = authService.getMe(authentication);
        MemberSchedule memberSchedule = memberScheduleService.getMemberSchedule(member_schedule_id);
        return memberScheduleService.editMemberSchedule(me, memberSchedule, newMemberSchedule);
    }

    @DeleteMapping("/{member_schedule_id}")
    public MemberSchedule deleteMemberSchedule(Authentication authentication, @PathVariable Long member_schedule_id) {
        Member me = authService.getMe(authentication);
        MemberSchedule memberSchedule = memberScheduleService.getMemberSchedule(member_schedule_id);
        memberScheduleService.deleteMemberSchedule(me, memberSchedule);
        return memberSchedule;
    }
}
