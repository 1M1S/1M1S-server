package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

    @OneToMany
    @JoinColumn(name = "interest_id")
    @Getter @Setter
    private List<Interest> interest;

    @Getter @Setter
    private Integer level;
}
