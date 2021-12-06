package com.m1s.m1sserver.api.curriculum;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    Iterable<Curriculum> findAllByInterestId(Long interest_id);
    Iterable<Curriculum> findAllByLevel(String level);
    Iterable<Curriculum> findAllByInterestIdAndLevel(Long interest_id, String level);
}
