package com.m1s.m1sserver;


import com.m1s.m1sserver.auth.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class UserStorage {

    @Getter @Setter
    private Member member;


}
