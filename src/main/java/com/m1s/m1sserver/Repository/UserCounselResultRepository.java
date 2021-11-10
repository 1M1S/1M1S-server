package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.CounselResult;
import com.m1s.m1sserver.Model.UserCounselResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCounselResultRepository extends JpaRepository<UserCounselResult, Integer> {
}
