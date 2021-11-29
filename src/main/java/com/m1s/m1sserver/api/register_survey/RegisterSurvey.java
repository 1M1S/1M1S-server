package com.m1s.m1sserver.api.register_survey;

import com.m1s.m1sserver.api.interest.Interest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class RegisterSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    @Setter @Getter
    private Interest interest;

    @Setter @Getter
    private Integer problemNumber;

    @Setter @Getter
    private String question;

    @Setter @Getter
    private String choices;
}
