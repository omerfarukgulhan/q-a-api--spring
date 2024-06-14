package com.ofg.qa.service;

import com.ofg.qa.dao.LikeRepository;
import com.ofg.qa.entity.Like;
import com.ofg.qa.entity.Post;
import com.ofg.qa.entity.User;
import com.ofg.qa.requests.LikeCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Like> getLikes(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent()) {
            return likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            return likeRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return likeRepository.findByPostId(postId.get());
        } else {
            return likeRepository.findAll();
        }
    }

    public Like getLikeById(Long id) {
        return likeRepository.findById(id).orElse(null);
    }

    public Like saveLike(LikeCreateRequest likeCreateRequest) {
        User user = userService.getUserById(likeCreateRequest.getUserId());
        Post post = postService.getPostById(likeCreateRequest.getPostId());

        if (user != null && post != null) {
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);
            return likeRepository.save(like);
        } else {
            return null;
        }
    }

    public void deleteLikeById(Long id) {
        try {
            likeRepository.deleteById(id);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
