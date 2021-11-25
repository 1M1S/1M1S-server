package com.m1s.m1sserver.api.ranking;

import com.m1s.m1sserver.api.ranking.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    @Query(value = "SELECT RANK FROM (\n" +
            "SELECT score, member_id,\n" +
            "DENSE_RANK() over ( ORDER BY score DESC ) as rank\n" +
            "FROM ranking WHERE interest_id = ?2\n" +
            ") AS r WHERE member_id = ?1", nativeQuery = true)
    Long getRank(Long user_id, Long interest_id);


    boolean existsByMemberIdAndInterestId(Long user_id, Long interest_id);
    Ranking findByMemberIdAndInterestId(Long user_id, Long interest_id);
}
