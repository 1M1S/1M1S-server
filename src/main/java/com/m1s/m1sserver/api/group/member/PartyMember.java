package com.m1s.m1sserver.api.group.member;

import com.m1s.m1sserver.api.group.Party;
import com.m1s.m1sserver.auth.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Builder
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
    private String authority;

    public Long getPartyId(){return party.getId();}
}
