package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {
}
