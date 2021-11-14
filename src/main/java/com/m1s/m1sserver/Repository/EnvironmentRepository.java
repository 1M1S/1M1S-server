package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
}
