package com.m1s.m1sserver.api.user.counsel_result;

import com.m1s.m1sserver.Model.MemberCounselResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCounselResultRepository extends JpaRepository<MemberCounselResult, Long> {
    Iterable<MemberCounselResult> findAllByMemberIdOrderByCounselTime(Long user_id);
    boolean existsById(Long member_counsel_result_id);
    MemberCounselResult findByResult(String result);
    void deleteById(Long member_counsel_result_id);
}
