package com.m1s.m1sserver.auth.refresh_token;

import com.m1s.m1sserver.auth.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Builder
@Entity
public class RefreshToken {
    @Id
    @OneToOne
    @JoinColumn(name="member_id")
    @NonNull
    @Getter @Setter
    private Member member;

    @Id
    @NonNull
    @Getter @Setter
    private String refreshToken;

}
