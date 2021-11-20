package com.m1s.m1sserver.auth.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Id
    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

}