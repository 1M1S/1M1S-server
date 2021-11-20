package com.m1s.m1sserver.api.post.comment;

import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.api.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @Getter @Setter
    private Post post;

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
