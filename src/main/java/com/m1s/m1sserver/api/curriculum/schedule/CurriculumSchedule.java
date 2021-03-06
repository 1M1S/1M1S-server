package com.m1s.m1sserver.api.curriculum.schedule;

import com.m1s.m1sserver.api.curriculum.Curriculum;
import com.m1s.m1sserver.api.interest.Interest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class CurriculumSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    @Getter @Setter
    private Curriculum curriculum;

    @Getter @Setter
    private String content;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    @Getter @Setter
    private Interest interest;
}
