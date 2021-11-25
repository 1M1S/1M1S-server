package com.m1s.m1sserver.api.register_survey;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

public interface RegisterSurveyRepository extends JpaRepository<RegisterSurvey, Long> {
    Iterable<RegisterSurvey> findAllByInterestId(Long interest_id, Sort sort);
}
