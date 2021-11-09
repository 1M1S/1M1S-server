package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
