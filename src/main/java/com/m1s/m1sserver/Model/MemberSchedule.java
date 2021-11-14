package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Getter @Setter
    private Member member;

    @Getter @Setter
    private String content;

    @Getter @Setter
    private LocalDateTime start_time;

    @Getter @Setter
    private LocalDateTime end_time;

    @Getter @Setter
    private boolean finish;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    @Getter @Setter
    private Interest interest;
}
