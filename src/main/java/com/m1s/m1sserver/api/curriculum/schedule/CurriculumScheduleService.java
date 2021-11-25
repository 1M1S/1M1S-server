package com.m1s.m1sserver.api.curriculum.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurriculumScheduleService {
    @Autowired
    private CurriculumScheduleRepository curriculumScheduleRepository;

    public Iterable<CurriculumSchedule> getCurriculums(Long curriculum_id){
        return curriculumScheduleRepository.findAllByCurriculumId(curriculum_id);
    }
}
