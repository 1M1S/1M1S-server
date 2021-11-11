package com.m1s.m1sserver.Repository;

import com.m1s.m1sserver.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
