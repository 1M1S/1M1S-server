package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class RegisterSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    @Setter @Getter
    private Interest interest;

    @Setter @Getter
    private Integer problem_number;

    @Setter @Getter
    private String question;

    @Setter @Getter
    private String choices;
}
