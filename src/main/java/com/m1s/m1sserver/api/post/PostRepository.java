package com.m1s.m1sserver.api.post;

import com.m1s.m1sserver.api.post.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

public interface PostRepository extends JpaRepository<Post, Long> {
    Iterable<Post> findByMemberId(Long user_id, Sort sort);
    Iterable<Post> findAllByInterestId(Long interest_id, Sort sort);

    @Override
    boolean existsById(Long post_id);

    @Query(value = "SELECT * FROM post WHERE member_id in (\n" +
            "SELECT member_id FROM party_member WHERE party_id = ?1 AND authority != \"승인대기\"\n" +
            ");", nativeQuery = true)
    Iterable<Post> findAllByPartyId(Long group_id);
}
