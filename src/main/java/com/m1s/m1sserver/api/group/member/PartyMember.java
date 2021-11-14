package com.m1s.m1sserver.api.group.member;

import com.m1s.m1sserver.api.group.Party;
import com.m1s.m1sserver.api.user.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class PartyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

    @ManyToOne
    @JoinColumn(name = "party_id")
    @Getter @Setter
    private Party party;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Getter @Setter
    private Member member;

    @Getter @Setter
    private Integer authority;
}
