package com.m1s.m1sserver.api.post;


import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Iterable<Post> getPosts(Long interest_id) {
        if(interest_id == null) return getAllPost();
        return postRepository.findAllByInterestId(interest_id, Sort.by(Sort.Direction.DESC, "writingDate"));
    }

    public Iterable<Post> getAllPost(){
        return postRepository.findAll(Sort.by("writingDate"));
    }

    @GetMapping("/{post_id}")
    public Post getPost(@PathVariable Long post_id) {
        if(!postRepository.existsById(post_id))throw new CustomException(ErrorCode.POST_NOT_FOUND);
        return postRepository.findById(post_id).get();
    }

}
