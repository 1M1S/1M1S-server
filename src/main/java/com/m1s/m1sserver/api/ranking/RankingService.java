package com.m1s.m1sserver.api.ranking;


import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class RankingService {

    @Autowired
    private RankingRepository rankingRepository;

    public Long getRank(Long user_id, Long interest_id) {
        if(!rankingRepository.existsByMemberIdAndInterestId(user_id, interest_id))throw new CustomException(ErrorCode.RANK_NOT_FOUND);
        return rankingRepository.getRank(user_id, interest_id);
    }

}
