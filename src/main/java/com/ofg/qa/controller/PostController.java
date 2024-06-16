package com.ofg.qa.controller;

import com.ofg.qa.entity.Post;
import com.ofg.qa.requests.PostCreateRequest;
import com.ofg.qa.requests.PostUpdateRequest;
import com.ofg.qa.responses.PostResponse;
import com.ofg.qa.service.PostService;
import com.ofg.qa.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/posts")
@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getPosts(@RequestParam Optional<Long> userId) {
        return new ApiResponse<>(true, "Data fetched successfully", postService.getPosts(userId));
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable long postId) {
        return new ApiResponse<>(true, "Data fetched successfully", postService.getPostByIdWithLikes(postId));
    }

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        return new ApiResponse<>(true, "Data added successfully", postService.savePost(postCreateRequest));
    }

    @PutMapping("/{postId}")
    public ApiResponse<PostResponse> updatePost(@PathVariable long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        PostResponse dbPost = postService.updatePost(postId, postUpdateRequest);
        if (dbPost != null) {
            return new ApiResponse<>(true, "Data updated successfully", dbPost);
        } else {
            return new ApiResponse<>(false, "Post not found", null);
        }
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Long> deleteUser(@PathVariable long postId) {
        postService.deletePostById(postId);
        return new ApiResponse<>(true, "Data deleted successfully", postId);
    }
}
