package com.m1s.m1sserver.api.user.schedule;

import com.m1s.m1sserver.api.user.schedule.MemberSchedule;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberScheduleRepository extends JpaRepository<MemberSchedule, Long> {
    Iterable<MemberSchedule> findAllByMemberId(Long user_id, Sort sort);
    void deleteAllByMemberId(Long user_id);
}
