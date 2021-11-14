package com.m1s.m1sserver.api.user.curriculum;

import com.m1s.m1sserver.api.admin.Curriculum;
import com.m1s.m1sserver.api.user.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
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
}
