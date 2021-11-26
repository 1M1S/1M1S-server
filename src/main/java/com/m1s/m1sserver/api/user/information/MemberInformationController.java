package com.m1s.m1sserver.api.user.information;

import com.m1s.m1sserver.api.user.schedule.MemberSchedule;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user/information")
public class MemberInformationController {
    @Autowired
    private MemberInformationService memberInformationService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;

    @GetMapping
    public @ResponseBody MemberInformation getMemberInformation(Authentication authentication) {
        Member me = authService.getMe(authentication);
        return memberInformationService.getMemberInfo(me);
    }

    @PutMapping
    public @ResponseBody MemberInformation editMemberInformation(Authentication authentication, @RequestBody MemberInformation newMemberInformation) {
        Member me = authService.getMe(authentication);
        MemberInformation targetMemberInformation = memberInformationService.getMemberInfo(me);
        return memberInformationService.editMemberInformation(me, targetMemberInformation, newMemberInformation);
    }
}
