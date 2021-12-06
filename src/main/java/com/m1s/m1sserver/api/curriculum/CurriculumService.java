package com.m1s.m1sserver.api.curriculum;


import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurriculumService {

    @Autowired
    private CurriculumRepository curriculumRepository;

    public Iterable<Curriculum> getCurriculums(Long interest_id, String level){
        if(interest_id == null)return getCurriculums(level);
        if(level == null)return getCurriculums(interest_id);
        return curriculumRepository.findAllByInterestIdAndLevel(interest_id, level);
    }

    public Iterable<Curriculum> getCurriculums(String level){
        if(level == null)return curriculumRepository.findAll();
        return curriculumRepository.findAllByLevel(level);
    }

    public Iterable<Curriculum> getCurriculums(Long interest_id){
        if(interest_id == null)return curriculumRepository.findAll();
        return curriculumRepository.findAllByInterestId(interest_id);
    }

    public Curriculum getCurriculum(Long curriculum_id) {
        if(!curriculumRepository.existsById(curriculum_id))throw new CustomException(ErrorCode.CURRICULUM_NOT_FOUND);
        return curriculumRepository.findById(curriculum_id).get();
    }
}
