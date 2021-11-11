package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    @Getter @Setter
    private Interest interest;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

    @Getter @Setter
    private LocalDateTime writing_date;
}
