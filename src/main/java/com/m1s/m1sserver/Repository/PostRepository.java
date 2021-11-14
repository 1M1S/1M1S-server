package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
