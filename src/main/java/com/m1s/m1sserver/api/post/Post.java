package com.m1s.m1sserver.api.post;

import com.m1s.m1sserver.api.admin.interest.Interest;
import com.m1s.m1sserver.auth.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    @Getter @Setter
    private Interest interest;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Getter @Setter
    private Member member;

    @JoinColumn(name = "writing_date")
    @Getter @Setter
    private LocalDateTime writingDate;
}
