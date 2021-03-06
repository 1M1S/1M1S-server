package com.m1s.m1sserver.api.user.curriculum;

import com.m1s.m1sserver.api.curriculum.Curriculum;
import com.m1s.m1sserver.api.curriculum.CurriculumRepository;
import com.m1s.m1sserver.api.curriculum.CurriculumService;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberRepository;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user/curriculum")
public class MemberCurriculumController {
    @Autowired
    private MemberCurriculumService memberCurriculumService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CurriculumService curriculumService;
    @Autowired
    private AuthService authService;

    @PostMapping
    public @ResponseBody MemberCurriculum addMemberCurriculum(Authentication authentication, @RequestParam Long curriculum_id) {
        MemberCurriculum foundMemberCurriculum = memberCurriculumService.getMemberCurriculum(authentication, curriculum_id);
        if(foundMemberCurriculum != null) throw new CustomException(ErrorCode.DUPLICATE_MEMBER_CURRICULUM);
        Member me = authService.getMe(authentication);
        Curriculum foundCurriculum = curriculumService.getCurriculum(curriculum_id);
        MemberCurriculum newCurriculum = memberCurriculumService.createMemberCurriculum(me, foundCurriculum);
        return memberCurriculumService.save(newCurriculum);
    }

    @GetMapping
    public @ResponseBody Iterable<MemberCurriculum> getMemberCurriculums(Authentication authentication) {
        Member me = authService.getMe(authentication);
        return memberCurriculumService.getMemberCurriculums(me);
    }

    @DeleteMapping
    public @ResponseBody MemberCurriculum deleteMemberCurriculum(Authentication authentication, @RequestParam Long curriculum_id) {
        return memberCurriculumService.deleteMemberCurriculum(authentication, curriculum_id);
    }

}
