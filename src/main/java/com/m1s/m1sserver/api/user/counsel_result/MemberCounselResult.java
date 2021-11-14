package com.m1s.m1sserver.api.user.counsel_result;

import com.m1s.m1sserver.api.admin.counsel_solution.CounselSolution;
import com.m1s.m1sserver.api.user.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberCounselResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Setter @Getter
    private Member member;

    @ManyToOne
    @JoinColumn(name = "counsel_solution_id")
    @Getter @Setter
    private CounselSolution counsel_solution;

    @Setter @Getter
    private LocalDateTime counsel_time;
}
