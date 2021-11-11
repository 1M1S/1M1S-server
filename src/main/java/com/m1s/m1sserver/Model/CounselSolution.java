package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CounselSolution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @Setter @Getter
    private String result;

    @Setter @Getter
    private String solution;
}