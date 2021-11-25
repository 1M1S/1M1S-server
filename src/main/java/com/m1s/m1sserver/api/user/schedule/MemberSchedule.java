package com.m1s.m1sserver.api.user.schedule;

import com.m1s.m1sserver.api.interest.Interest;
import com.m1s.m1sserver.auth.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class MemberSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Getter @Setter
    private Member member;

    @Getter @Setter
    private String content;

    @JoinColumn(name = "start_time")
    @Getter @Setter
    private LocalDateTime startTime;

    @JoinColumn(name = "end_time")
    @Getter @Setter
    private LocalDateTime endTime;

    @Getter @Setter
    private Boolean finish;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    @Getter @Setter
    private Interest interest;

    int calScore(String score_per_minute) {
        return Integer.parseInt(Long.toString(ChronoUnit.MINUTES.between(startTime, endTime))) * Integer.parseInt(score_per_minute);
    }
}
