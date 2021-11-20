package com.m1s.m1sserver.api.user.counsel_result;

import com.m1s.m1sserver.api.admin.counsel_solution.CounselSolutionRepository;
import com.m1s.m1sserver.auth.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/user/{user_id}/counsel-result")
public class MemberCounselResultController {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberCounselResultRepository memberCounselResultRepository;
    @Autowired
    CounselSolutionRepository counselSolutionRepository;

    @PostMapping
    public MemberCounselResult addMemberCounselResult(@PathVariable Long user_id, @RequestParam String result) {
        MemberCounselResult m = new MemberCounselResult();
        m.setMember(memberRepository.findById(user_id).get());
        m.setCounselSolution(counselSolutionRepository.findByResult(result));
        m.setCounselTime(LocalDateTime.now());
        memberCounselResultRepository.save(m);
        return m;
    }

    @GetMapping
    public Iterable<MemberCounselResult> getMemberCounselResult(@PathVariable Long user_id) {
        return memberCounselResultRepository.findAllByMemberId(user_id, Sort.by("counselTime"));
    }

    @DeleteMapping("/{member_counsel_result_id}")
    public MemberCounselResult delMemberCounselResult(@PathVariable Long user_id, @PathVariable Long member_counsel_result_id) {
        MemberCounselResult m = memberCounselResultRepository.findById(member_counsel_result_id).get();
        memberCounselResultRepository.deleteById(member_counsel_result_id);
        return m;
    }
}
