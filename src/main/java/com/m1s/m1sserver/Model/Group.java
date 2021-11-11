package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private String name;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    @Getter @Setter
    private Interest interest;

    @Getter @Setter
    private String goal;

    @Getter @Setter
    private Integer maximum_number_of_people;

    @Getter @Setter
    private boolean recruit;
}
