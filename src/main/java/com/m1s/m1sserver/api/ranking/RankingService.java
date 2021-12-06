package com.m1s.m1sserver.api.ranking;


import com.m1s.m1sserver.api.admin.enviroment.EnvironmentService;
import com.m1s.m1sserver.api.interest.Interest;
import com.m1s.m1sserver.api.user.schedule.MemberSchedule;
import com.m1s.m1sserver.auth.member.Member;
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

    @Autowired
    private EnvironmentService environmentService;

    public Long getRankNum(Member member, Interest interest) {
        if(!rankingRepository.existsByMemberIdAndInterestId(member.getId(), interest.getId()))throw new CustomException(ErrorCode.RANK_NOT_FOUND);
        return rankingRepository.getRank(member.getId(), interest.getId());
    }

    public Ranking getRanking(Member member, Interest interest){
        if(!rankingRepository.existsByMemberIdAndInterestId(member.getId(), interest.getId()))throw new CustomException(ErrorCode.RANK_NOT_FOUND);
        return rankingRepository.findByMemberIdAndInterestId(member.getId(), interest.getId());
    }

    public Iterable<Ranking> getTopRankings(Long interest_id) {
        return rankingRepository.getTopRankings(interest_id);
    }

    public Ranking createRanking(Member member, Interest interest){
        return save(Ranking.builder()
                .member(member)
                .interest(interest)
                .score(0)
                .build());
    }

    public Ranking save(Ranking ranking){
        return rankingRepository.save(ranking);
    }

    // 점수를 추가하는 기능이므로 함수명으로 editScore보다 addScore가 더 적절해보인다.
    public void addScore(Ranking ranking, MemberSchedule memberSchedule){
        final String score_per_minute = environmentService.getEnvironment("score_per_minute").getValue();
        int score = ranking.getScore() + memberSchedule.calculateScore(score_per_minute);
        ranking.setScore(score);
    }

    public void deleteScore(Ranking ranking, MemberSchedule memberSchedule){
        final String score_per_minute = environmentService.getEnvironment("score_per_minute").getValue();
        int score = ranking.getScore() - memberSchedule.calculateScore(score_per_minute);
        ranking.setScore(score);
    }

    public void deleteRankings(Member member){
        rankingRepository.deleteAllByMemberId(member.getId());
    }
}
