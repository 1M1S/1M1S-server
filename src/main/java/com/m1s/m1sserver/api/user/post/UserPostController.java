package com.m1s.m1sserver.api.user.post;

import com.m1s.m1sserver.api.post.Post;
import com.m1s.m1sserver.api.post.PostRepository;
import com.m1s.m1sserver.api.post.PostService;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/post")
public class UserPostController {
    @Autowired
    private PostService postService;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @PostMapping
    public Post addPost(Authentication authentication, @RequestBody Post post){
        Member me = authService.getMe(authentication);
        return postService.createPost(me, post);
    }

    @GetMapping
    public Iterable<Post> getPosts(Authentication authentication) {
        Member me = authService.getMe(authentication);
        return postService.getPosts(me);
    }

    @GetMapping("/{post_id}")
    public Boolean checkPost(Authentication authentication, @PathVariable Long post_id) {
        Member me = authService.getMe(authentication);
        Post targetPost = postService.getPost(post_id);
        return postService.checkOwner(me, targetPost);
    }

    @PutMapping("/{post_id}")
    public Post editPost(Authentication authentication, @PathVariable Long post_id, @RequestBody Post newPost) {
        Member member = authService.getMe(authentication);
        Post oldPost = postService.getPost(post_id);
        return postService.editPost(member, oldPost, newPost);
    }

    @DeleteMapping("/{post_id}")
    public Post deletePost(Authentication authentication, @PathVariable Long post_id) {
        Member me = authService.getMe(authentication);
        Post targetPost = postService.getPost(post_id);
        return targetPost;
    }
}
