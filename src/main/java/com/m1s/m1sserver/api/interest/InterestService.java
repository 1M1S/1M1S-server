package com.m1s.m1sserver.api.interest;


import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestService {
    @Autowired
    private InterestRepository interestRepository;

    public Iterable<Interest> getInterests(){
        return interestRepository.findAll();
    }

    public Interest getInterest(Long interest_id){
        if(!interestRepository.existsById(interest_id))throw new CustomException(ErrorCode.INTEREST_NOT_FOUND);
        return interestRepository.findById(interest_id).get();
    }

}
