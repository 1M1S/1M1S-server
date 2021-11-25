package com.m1s.m1sserver.api.counsel_solution;

import com.m1s.m1sserver.api.user.counsel_result.MemberCounselResult;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class CounselSolutionService {
    @Autowired
    CounselSolutionRepository counselSolutionRepository;

    public CounselSolution getCounselSolution(String result){
        CounselSolution counselSolution = counselSolutionRepository.findByResult(result);
        if(counselSolution == null)throw new CustomException(ErrorCode.COUNSEL_SOLUTION_NOT_FOUND);
        return counselSolution;
    }
    public Iterable<CounselSolution> getCounselSolutions(){
        return counselSolutionRepository.findAll();
    }
}
