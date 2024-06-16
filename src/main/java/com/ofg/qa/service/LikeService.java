package com.ofg.qa.service;

import com.ofg.qa.dao.LikeRepository;
import com.ofg.qa.entity.Like;
import com.ofg.qa.entity.Post;
import com.ofg.qa.entity.User;
import com.ofg.qa.requests.LikeCreateRequest;
import com.ofg.qa.responses.LikeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<LikeResponse> getLikes(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if (userId.isPresent() && postId.isPresent()) {
            list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            list = likeRepository.findByPostId(postId.get());
        } else {
            list = likeRepository.findAll();
        }
        return list.stream().map(LikeResponse::new).collect(Collectors.toList());
    }

    public LikeResponse getLikeById(Long id) {
        return new LikeResponse(likeRepository.findById(id).orElse(null));
    }

    public LikeResponse saveLike(LikeCreateRequest likeCreateRequest) {
        User user = userService.getUserById(likeCreateRequest.getUserId());
        Post post = postService.getPostById(likeCreateRequest.getPostId());

        if (user != null && post != null) {
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);
            return new LikeResponse(likeRepository.save(like));
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
