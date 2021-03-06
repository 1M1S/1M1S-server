package com.m1s.m1sserver.api.post.comment;

import com.m1s.m1sserver.api.post.Post;
import com.m1s.m1sserver.auth.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

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

    @Getter @Setter
    private LocalDateTime writingDate;
}
