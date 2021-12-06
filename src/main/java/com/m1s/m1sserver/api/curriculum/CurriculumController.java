package com.m1s.m1sserver.api.curriculum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/curriculum")
public class CurriculumController {
    @Autowired
    private CurriculumService curriculumService;

    @GetMapping
    public Iterable<Curriculum> getCurriculums(@RequestParam(required = false) Long interest_id, @RequestParam(required = false) String level) {
        return curriculumService.getCurriculums(interest_id, level);

        /*if(interest_id != null) {
            if(level != null) return curriculumRepository.findAllByInterestIdAndLevel(interest_id, level);
            else return curriculumRepository.findAllByInterestId(interest_id);
        }
        else {
            if(level != null) return curriculumRepository.findAllByLevel(level);
            else return curriculumRepository.findAll();
        }*/
    }
}
