package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class UserSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

    @Getter @Setter
    private String content;

    @Getter @Setter
    private LocalDateTime start_time;

    @Getter @Setter
    private LocalDateTime end_time;

    @Getter @Setter
    private boolean finish;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    @Getter @Setter
    private Interest interest;
}
