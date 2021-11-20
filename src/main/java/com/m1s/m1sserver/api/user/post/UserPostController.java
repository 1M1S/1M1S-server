package com.m1s.m1sserver.api.user.post;

import com.m1s.m1sserver.api.post.Post;
import com.m1s.m1sserver.api.post.PostRepository;
import com.m1s.m1sserver.auth.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/{user_id}/post")
public class UserPostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;

    @PostMapping
    public Post addPost(@PathVariable Long user_id, @RequestBody Post p) {
        p.setMember(memberRepository.findById(user_id).get());
        p.setWritingDate(LocalDateTime.now());
        postRepository.save(p);
        return p;
    }

    @GetMapping
    public Iterable<Post> getPost(@PathVariable Long user_id) {
        return postRepository.findByMemberId(user_id, Sort.by(Sort.Direction.DESC, "writingDate"));
    }

    @GetMapping("/{post_id}")
    public Boolean checkPost(@PathVariable Long user_id, @PathVariable Long post_id) {
        Post p = postRepository.findById(post_id).get();
        return p.getMember().getId().equals(user_id);
    }

    @PutMapping("/{post_id}")
    public ResponseEntity<Post> editPost(@PathVariable Long user_id, @PathVariable Long post_id, @RequestBody Post p) {
        Optional<Post> o = postRepository.findById(post_id);
        // 일정이 없는 경우 예외처리
        if(o.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Post edit = o.get();
        // 작성자가 유저와 일치하지 않는 경우 예외처리
        if(!edit.getMember().getId().equals(user_id)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(p.getInterest() != null) edit.setInterest(p.getInterest());
        if(p.getTitle() != null) edit.setTitle(p.getTitle());
        if(p.getContent() != null) edit.setContent(p.getContent());

        postRepository.save(edit);
        return new ResponseEntity<>(edit, HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<Post> delPost(@PathVariable Long user_id, @PathVariable Long post_id) {
        Optional<Post> o = postRepository.findById(post_id);
        // 일정이 없는 경우 예외처리
        if(o.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Post p = o.get();
        if(!p.getMember().getId().equals(user_id)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        postRepository.deleteById(post_id);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
