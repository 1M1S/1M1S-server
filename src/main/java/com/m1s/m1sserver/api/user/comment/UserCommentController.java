package com.m1s.m1sserver.api.user.comment;

import com.m1s.m1sserver.api.post.Post;
import com.m1s.m1sserver.api.post.PostRepository;
import com.m1s.m1sserver.api.post.PostService;
import com.m1s.m1sserver.api.post.comment.Comment;
import com.m1s.m1sserver.api.post.comment.CommentRepository;
import com.m1s.m1sserver.api.post.comment.CommentService;
import com.m1s.m1sserver.auth.AuthService;
import com.m1s.m1sserver.auth.member.Member;
import com.m1s.m1sserver.auth.member.MemberRepository;
import com.m1s.m1sserver.auth.member.MemberService;
import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/comment")
public class UserCommentController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthService authService;


    @PostMapping
    public Comment addComment(Authentication authentication, @RequestParam Long post_id, @RequestBody Comment comment) {
        Member foundMember = authService.getMe(authentication);
        Post foundPost = postService.getPost(post_id);
        Comment newComment = commentService.createComment(foundMember, foundPost, comment);
        return commentService.save(newComment);
    }

    @GetMapping
    public Iterable<Comment> getComments(@PathVariable Long user_id) {
        return commentService.getComments(user_id);
    }

    @GetMapping("/{comment_id}")
    public Boolean checkComment(Authentication authentication, @PathVariable Long comment_id) {
        Long user_id = (Long)authentication.getPrincipal();
        Comment foundComment = commentService.getComment(comment_id);
        return commentService.checkOwnerOfComment(user_id, foundComment);
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<Comment> editComment(Authentication authentication, @PathVariable Long comment_id, @RequestBody Comment newComment) {
        Comment oldComment = commentService.getComment(comment_id);
        if (!commentService.checkOwnerOfComment
                (authService.getMyId(authentication), oldComment))
            throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        commentService.editComment(oldComment, newComment);
        return new ResponseEntity<>(oldComment, HttpStatus.OK);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<Comment> deleteComment(Authentication authentication, @PathVariable Long comment_id) {
        Comment targetComment = commentService.getComment(comment_id);
        if (!commentService.checkOwnerOfComment
                (authService.getMyId(authentication), targetComment))
            throw new CustomException(ErrorCode.NO_AUTHENTICATION);
        commentService.deleteComment(comment_id);
        return new ResponseEntity<>(targetComment, HttpStatus.OK);
    }
}