package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.CounselResult;
import com.m1s.m1sserver.Model.CounselSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounselResultRepository extends JpaRepository<CounselResult, Integer> {
}
