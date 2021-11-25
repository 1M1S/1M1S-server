package com.m1s.m1sserver.api.counsel_survey;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CounselSurveyService {
    @Autowired
    private CounselSurveyRepository counselSurveyRepository;

    public Iterable<CounselSurvey> getCounselSurveys(Sort sort){
        return counselSurveyRepository.findAll(sort);
    }

}
