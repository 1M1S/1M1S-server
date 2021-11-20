package com.m1s.m1sserver.api.user.information;

import com.m1s.m1sserver.auth.member.Member;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    @Getter @Setter
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    @NonNull
    @Getter @Setter
    private Member member;


    @NonNull
    @Getter @Setter
    private String name;

    @NonNull
    @Getter @Setter
    private String nickname;

    @NonNull
    @Getter @Setter
    private String gender;

    @NonNull
    @Getter @Setter
    private String phone;

    @NonNull
    @Getter @Setter
    private String email;

    @JoinColumn(name = "register_date")
    @NonNull
    @Getter @Setter
    private LocalDateTime registerDate;
}
