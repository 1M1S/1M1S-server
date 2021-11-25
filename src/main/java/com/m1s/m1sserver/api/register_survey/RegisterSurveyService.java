package com.m1s.m1sserver.api.register_survey;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class RegisterSurveyService {
    @Autowired
    private RegisterSurveyRepository registerSurveyRepository;

    public Iterable<RegisterSurvey> getRegisterSurvey(Long interest_id) {
        return registerSurveyRepository.findAllByInterestId(interest_id, Sort.by("problemNumber"));
    }

}
