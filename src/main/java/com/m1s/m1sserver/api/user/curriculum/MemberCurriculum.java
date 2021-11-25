package com.m1s.m1sserver.api.user.curriculum;

import com.m1s.m1sserver.api.curriculum.Curriculum;
import com.m1s.m1sserver.auth.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Builder
public class MemberCurriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Getter @Setter
    private Member member;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    @Getter @Setter
    private Curriculum curriculum;

    public Long getMemberId(){return member.getId();}
}
