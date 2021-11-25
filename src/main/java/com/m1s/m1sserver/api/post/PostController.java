package com.m1s.m1sserver.api.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public Iterable<Post> getAllPost(@RequestParam(required = false) Long interest_id) {
        return postService.getPosts(interest_id);
    }

    @GetMapping("/{post_id}")
    public Post getPost(@PathVariable Long post_id) {

        return postService.getPost(post_id);
    }
}
