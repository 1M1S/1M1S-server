package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    @Getter @Setter
    private Interest interest;

    @Getter @Setter
    private int score;
}
