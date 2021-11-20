package com.m1s.m1sserver.api.user.curriculum;

import com.m1s.m1sserver.api.admin.curriculum.CurriculumRepository;
import com.m1s.m1sserver.auth.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user/{user_id}/curriculum")
public class MemberCurriculumController {
    @Autowired
    private MemberCurriculumRepository memberCurriculumRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CurriculumRepository curriculumRepository;

    @PostMapping
    public @ResponseBody MemberCurriculum addMemberCurriculum(@PathVariable Long user_id, @RequestParam Long curriculum_id) {
        MemberCurriculum m = new MemberCurriculum();
        m.setMember(memberRepository.findById(user_id).get());
        m.setCurriculum(curriculumRepository.findById(curriculum_id).get());
        memberCurriculumRepository.save(m);
        return m;
    }

    @GetMapping
    public @ResponseBody Iterable<MemberCurriculum> getMemberCurriculum(@PathVariable Long user_id) {
        return memberCurriculumRepository.findAllByMemberId(user_id);
    }

    @DeleteMapping("/{member_curriculum_id}")
    public @ResponseBody MemberCurriculum delMemberCurriculum(@PathVariable Long member_curriculum_id) {
        MemberCurriculum m = memberCurriculumRepository.findById(member_curriculum_id).get();
        memberCurriculumRepository.deleteById(member_curriculum_id);
        return m;
    }

}
