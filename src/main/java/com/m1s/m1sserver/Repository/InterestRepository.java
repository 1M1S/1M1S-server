package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface InterestRepository extends JpaRepository<Interest, Long> {
}
