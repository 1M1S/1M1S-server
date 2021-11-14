package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {
}
