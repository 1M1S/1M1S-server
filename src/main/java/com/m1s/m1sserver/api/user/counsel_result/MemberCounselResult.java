package com.m1s.m1sserver.api.user.counsel_result;

import com.m1s.m1sserver.api.counsel_solution.CounselSolution;
import com.m1s.m1sserver.auth.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCounselResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Setter @Getter
    private Member member;

    @Column(length = 1000)
    @Getter @Setter
    private String counselSolution;

    @Setter @Getter
    private LocalDateTime counselTime;

    public Long getMemberId(){return member.getId();}
}
