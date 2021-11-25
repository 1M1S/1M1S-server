package com.m1s.m1sserver.api.interest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interest")
public class InterestController {
    @Autowired
    private InterestService interestService;

    @GetMapping
    public Iterable<Interest> getInterests() {
        return interestService.getInterests();
    }
}
