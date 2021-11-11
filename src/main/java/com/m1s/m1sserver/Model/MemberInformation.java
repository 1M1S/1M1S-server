package com.m1s.m1sserver.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @OneToOne
    @JoinColumn(name = "member_id")
    @Getter @Setter
    private Member member;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String nickname;

    @Getter @Setter
    private String gender;

    @Getter @Setter
    private String phone;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private LocalDateTime register_date;
}
