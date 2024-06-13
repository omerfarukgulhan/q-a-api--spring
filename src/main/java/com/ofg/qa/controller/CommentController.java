package com.ofg.qa.controller;

import com.ofg.qa.entity.Comment;
import com.ofg.qa.requests.CommentCreateRequest;
import com.ofg.qa.requests.CommentUpdateRequest;
import com.ofg.qa.service.CommentService;
import com.ofg.qa.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ApiResponse<List<Comment>> getComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
        return new ApiResponse<>(true, "Data fetched successfully", commentService.getComments(userId, postId));
    }

    @GetMapping("/{commentId}")
    public ApiResponse<Comment> getComment(@PathVariable long commentId) {
        return new ApiResponse<>(true, "Data fetched successfully", commentService.getCommentById(commentId));
    }

    @PostMapping
    public ApiResponse<Comment> createComment(@RequestBody CommentCreateRequest commentCreateRequest) {
        return new ApiResponse<>(true, "Data added successfully", commentService.saveComment(commentCreateRequest));
    }

    @PutMapping("/{commentId}")
    public ApiResponse<Comment> updateComment(@PathVariable long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest) {
        Comment dbComment = commentService.updateComment(commentId, commentUpdateRequest);
        if (dbComment != null) {
            return new ApiResponse<>(true, "Data updated successfully", dbComment);
        } else {
            return new ApiResponse<>(false, "Comment not found", null);
        }
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Long> deleteComment(@PathVariable long commentId) {
        commentService.deleteCommentById(commentId);
        return new ApiResponse<>(true, "Data deleted successfully", commentId);
    }
}
