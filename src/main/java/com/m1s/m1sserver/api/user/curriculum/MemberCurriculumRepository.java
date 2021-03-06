package com.m1s.m1sserver.api.user.curriculum;

import com.m1s.m1sserver.api.curriculum.Curriculum;
import com.m1s.m1sserver.api.user.curriculum.MemberCurriculum;
import com.m1s.m1sserver.auth.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCurriculumRepository extends JpaRepository<MemberCurriculum, Long> {
    Iterable<MemberCurriculum> findAllByMember(Member member);
    MemberCurriculum findByMemberAndCurriculumId(Member member, Long curriculum_id);
    void deleteAllByMember(Member member);
}
