package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class MemberCurriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private Member member;

    @OneToMany
    @JoinColumn(name = "curriculum_id")
    @Getter @Setter
    private Curriculum curriculum;
}