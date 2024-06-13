package com.ofg.qa.service;

import com.ofg.qa.dao.CommentRepository;
import com.ofg.qa.entity.Comment;
import com.ofg.qa.entity.Post;
import com.ofg.qa.entity.User;
import com.ofg.qa.requests.CommentCreateRequest;
import com.ofg.qa.requests.CommentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getComments(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent()) {
            return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            return commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        } else {
            return commentRepository.findAll();
        }
    }

    public Comment getCommentById(long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public Comment saveComment(CommentCreateRequest commentCreateRequest) {
        User user = userService.getUserById(commentCreateRequest.getUserId());
        Post post = postService.getPostById(commentCreateRequest.getPostId());

        if (user != null && post != null) {
            Comment commentToSave = new Comment();
            commentToSave.setId(commentCreateRequest.getId());
            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setText(commentCreateRequest.getText());
            commentToSave.setCreateDate(new Date());
            return commentRepository.save(commentToSave);
        } else {
            return null;
        }
    }

    public Comment updateComment(long id, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> dbComment = commentRepository.findById(id);
        if (dbComment.isPresent()) {
            Comment updatedComment = dbComment.get();
            updatedComment.setText(commentUpdateRequest.getText());
            commentRepository.save(updatedComment);
            return updatedComment;
        } else {
            return null;
        }
    }

    public void deleteCommentById(long id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void deleteComment(Comment Comment) {
        commentRepository.delete(Comment);
    }
}