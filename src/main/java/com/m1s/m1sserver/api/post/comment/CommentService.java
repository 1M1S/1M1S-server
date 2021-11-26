package com.m1s.m1sserver.api.post.comment;


import com.m1s.m1sserver.api.post.Post;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberService;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;

    public Iterable<Comment> getComments(Long post_id){
        return commentRepository.findByPostId(post_id, Sort.by(Sort.Direction.DESC, "writingDate"));
    }

    public Comment getComment(Long comment_id){
        if(!commentRepository.existsById(comment_id))throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        return commentRepository.findById(comment_id).get();
    }
    public Comment editComment(Member member, Comment oldComment, Comment newComment){
        checkOwner(member, oldComment);
        if (newComment.getContent() != null) oldComment.setContent(newComment.getContent());
        return commentRepository.save(oldComment);
    }
    public boolean checkOwner(Member member, Comment comment){
        if(member.getId() != comment.getMemberId())throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        return true;
    }

    public void deleteComment(Member member, Comment comment){
        checkOwner(member, comment);
        deleteComment(comment.getId());
    }

    public void deleteComment(Long comment_id){
        commentRepository.deleteById(comment_id);
    }

    public Comment createComment(Member member, Post post, Comment comment){
        return save(Comment.builder()
                .member(member)
                .post(post)
                .content(comment.getContent())
                .writing_date(LocalDateTime.now())
                .build());
    }

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

}
