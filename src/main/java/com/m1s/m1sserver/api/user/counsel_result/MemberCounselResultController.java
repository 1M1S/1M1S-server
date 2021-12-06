package com.m1s.m1sserver.api.user.counsel_result;

import com.m1s.m1sserver.api.counsel_solution.CounselSolution;
import com.m1s.m1sserver.api.counsel_solution.CounselSolutionRepository;
import com.m1s.m1sserver.api.counsel_solution.CounselSolutionService;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberService;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/user/counsel-result")
public class MemberCounselResultController {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberCounselResultService memberCounselResultService;

    @Autowired
    CounselSolutionService counselSolutionService;

    @Autowired
    AuthService authService;

    @PostMapping
    public MemberCounselResult addMemberCounselResult(Authentication authentication, @RequestBody String result) {
        Member member = authService.getMe(authentication);
        MemberCounselResult createdMemberCounselResult = memberCounselResultService.createMemberCounselResult(member, result);
        memberCounselResultService.save(createdMemberCounselResult);
        return createdMemberCounselResult;
    }

    @GetMapping
    public Iterable<MemberCounselResult> getMemberCounselResults(Authentication authentication) {
        Member me = authService.getMe(authentication);
        return memberCounselResultService.getMemberCounselResults(me);
    }

    @DeleteMapping("/{member_counsel_result_id}")
    public MemberCounselResult deleteMemberCounselResult(Authentication authentication, @PathVariable Long member_counsel_result_id) {
        Member me = authService.getMe(authentication);
        MemberCounselResult targetMemberCounselResult = memberCounselResultService.getMemberCounselResult(member_counsel_result_id);
        memberCounselResultService.deleteMemberCounselResult(me, targetMemberCounselResult);
        return targetMemberCounselResult;
    }
}
