package com.m1s.m1sserver.api.user.curriculum;

import com.m1s.m1sserver.api.user.curriculum.MemberCurriculum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCurriculumRepository extends JpaRepository<MemberCurriculum, Long> {
    Iterable<MemberCurriculum> findAllByMemberId(Long user_id);
    void deleteAllByMemberId(Long user_id);
}
