package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

public class UserCounselResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter @Getter
    private User user;

    @ManyToOne
    @Getter @Setter
    private CounselResult counsel_result;

    @Setter @Getter
    private LocalDateTime counsel_time;
}
