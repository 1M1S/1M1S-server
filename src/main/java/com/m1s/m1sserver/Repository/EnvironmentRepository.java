package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.Environment;
import com.m1s.m1sserver.Model.UserCounselResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvironmentRepository extends JpaRepository<Environment, Integer> {
}
