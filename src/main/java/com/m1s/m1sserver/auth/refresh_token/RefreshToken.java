package com.m1s.m1sserver.auth.refresh_token;

import com.m1s.m1sserver.auth.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@IdClass(RefreshTokenId.class)
public class RefreshToken implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name="member_id")
    @Getter @Setter
    private Member member;

    @Id
    @NonNull
    @Getter @Setter
    private String refreshToken;

}


@Getter @Setter
class RefreshTokenId implements Serializable{
    private Member member;
    private String refreshToken;

}
