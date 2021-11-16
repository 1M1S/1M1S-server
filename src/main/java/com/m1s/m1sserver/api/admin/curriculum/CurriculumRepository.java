package com.m1s.m1sserver.api.admin.curriculum;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    Iterable<Curriculum> findAllByInterestId(Long interest_id);
    Iterable<Curriculum> findAllByLevel(Integer level);
    Iterable<Curriculum> findAllByInterestIdAndLevel(Long interest_id, Integer level);
}
