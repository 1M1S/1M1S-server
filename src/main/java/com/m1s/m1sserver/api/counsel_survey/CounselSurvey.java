package com.m1s.m1sserver.api.counsel_survey;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class CounselSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

    @Setter @Getter
    private Integer problem_number;

    @Setter @Getter
    private String question;

    @Setter @Getter
    private String choices;
}
