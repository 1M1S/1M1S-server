package com.m1s.m1sserver.api.user.comment;

import com.m1s.m1sserver.api.post.PostRepository;
import com.m1s.m1sserver.api.post.comment.Comment;
import com.m1s.m1sserver.api.post.comment.CommentRepository;
import com.m1s.m1sserver.auth.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/{user_id}/comment")
public class UserCommentController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberRepository memberRepository;

    @PostMapping
    public Comment addComment(@PathVariable Long user_id, @RequestParam Long post_id, @RequestBody Comment c) {
        c.setMember(memberRepository.findById(user_id).get());
        c.setPost(postRepository.findById(post_id).get());
        c.setWritingDate(LocalDateTime.now());
        commentRepository.save(c);
        return c;
    }

    @GetMapping
    public Iterable<Comment> getComment(@PathVariable Long user_id) {
        return commentRepository.findByMemberId(user_id, Sort.by(Sort.Direction.DESC, "writingDate"));
    }

    @GetMapping("/{comment_id}")
    public Boolean checkComment(@PathVariable Long user_id, @PathVariable Long comment_id) {
        Comment c = commentRepository.findById(comment_id).get();
        return c.getMember().getId().equals(user_id);
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<Comment> editComment(@PathVariable Long user_id, @PathVariable Long comment_id, @RequestBody Comment c) {
        Optional<Comment> o = commentRepository.findById(comment_id);
        // 일정이 없는 경우 예외처리
        if (o.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Comment edit = o.get();
        // 작성자가 유저와 일치하지 않는 경우 예외처리
        if (!edit.getMember().getId().equals(user_id)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (c.getContent() != null) edit.setContent(c.getContent());

        commentRepository.save(edit);
        return new ResponseEntity<>(edit, HttpStatus.OK);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<Comment> delComment(@PathVariable Long user_id, @PathVariable Long comment_id) {
        Optional<Comment> o = commentRepository.findById(comment_id);
        // 일정이 없는 경우 예외처리
        if (o.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Comment c = o.get();
        if (!c.getMember().getId().equals(user_id)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        commentRepository.deleteById(comment_id);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
}