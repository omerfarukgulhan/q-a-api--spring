package com.ofg.qa.controller;

import com.ofg.qa.entity.Like;
import com.ofg.qa.requests.LikeCreateRequest;
import com.ofg.qa.service.LikeService;
import com.ofg.qa.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/likes")
@RestController
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public ApiResponse<List<Like>> getLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
        return new ApiResponse<>(true, "Data fetched successfully", likeService.getLikes(userId, postId));
    }

    @GetMapping("/{likeId}")
    public ApiResponse<Like> getLike(@PathVariable long likeId) {
        return new ApiResponse<>(true, "Data fetched successfully", likeService.getLikeById(likeId));
    }

    @PostMapping
    public ApiResponse<Like> createLike(@RequestBody LikeCreateRequest likeCreateRequest) {
        return new ApiResponse<>(true, "Data added successfully", likeService.saveLike(likeCreateRequest));
    }

    @DeleteMapping("/{likeId}")
    public ApiResponse<Long> deleteLike(@PathVariable long likeId) {
        likeService.deleteLikeById(likeId);
        return new ApiResponse<>(true, "Data deleted successfully", likeId);
    }
}
