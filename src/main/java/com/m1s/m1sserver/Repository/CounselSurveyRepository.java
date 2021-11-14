package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.CounselSurvey;
import com.m1s.m1sserver.Model.RegisterSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounselSurveyRepository extends JpaRepository<CounselSurvey, Long> {
}
