package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

public class CounselSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @Setter @Getter
    private Integer problem_number;

    @Setter @Getter
    private String question;

    @Setter @Getter
    private String choices;
}
