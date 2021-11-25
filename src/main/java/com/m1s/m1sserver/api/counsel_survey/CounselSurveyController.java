package com.m1s.m1sserver.api.counsel_survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/counsel-survey")
public class CounselSurveyController {
    @Autowired
    private CounselSurveyService counselSurveyService;

    @GetMapping
    public Iterable<CounselSurvey> getCounselSurvey() {
        return counselSurveyService.getCounselSurveys(Sort.by("problemNumber"));
    }
}
