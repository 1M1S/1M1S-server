package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.UserCurriculum;
import com.m1s.m1sserver.Model.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserCurriculumRepository extends JpaRepository<UserCurriculum, Integer> {
}
