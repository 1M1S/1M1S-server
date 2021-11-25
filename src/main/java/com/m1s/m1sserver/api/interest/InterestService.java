package com.m1s.m1sserver.api.interest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestService {
    @Autowired
    private InterestRepository interestRepository;

    public Iterable<Interest> getInterests(){
        return interestRepository.findAll();
    }

}
