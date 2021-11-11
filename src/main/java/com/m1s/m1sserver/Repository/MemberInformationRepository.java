package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.Member;
import com.m1s.m1sserver.Model.MemberInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInformationRepository extends JpaRepository<MemberInformation, Long> {
}
