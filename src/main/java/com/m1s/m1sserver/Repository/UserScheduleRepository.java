package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Integer> {
}
