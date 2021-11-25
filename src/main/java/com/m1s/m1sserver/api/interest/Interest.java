package com.m1s.m1sserver.api.interest;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

    @Getter @Setter
    private String subject;
}
