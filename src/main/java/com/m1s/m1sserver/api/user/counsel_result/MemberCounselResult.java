package com.m1s.m1sserver.api.user.counsel_result;

import com.m1s.m1sserver.api.counsel_solution.CounselSolution;
import com.m1s.m1sserver.auth.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
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
    private CounselSolution counselSolution;

    @Setter @Getter
    private LocalDateTime counselTime;

    public Long getMemberId(){return member.getId();}
}
