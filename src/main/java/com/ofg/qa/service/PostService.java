package com.ofg.qa.service;

import com.ofg.qa.dao.LikeRepository;
import com.ofg.qa.dao.PostRepository;
import com.ofg.qa.entity.Post;
import com.ofg.qa.entity.User;
import com.ofg.qa.requests.PostCreateRequest;
import com.ofg.qa.requests.PostUpdateRequest;
import com.ofg.qa.responses.LikeResponse;
import com.ofg.qa.responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final LikeRepository likeRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.likeRepository = likeRepository;
    }

    public List<PostResponse> getPosts(Optional<Long> userId) {
        List<Post> list;
        if (userId.isPresent()) {
            list = postRepository.findByUserId(userId.get());
        } else {
            list = postRepository.findAll();
        }
        return list.stream()
                .map(post ->
                        new PostResponse(post,
                                getLikesForPost(post.getId())))
                .collect(Collectors.toList());
    }

    public Post getPostById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public PostResponse getPostByIdWithLikes(long id) {
        return new PostResponse(postRepository.findById(id).orElse(null), getLikesForPost(id));
    }

    public PostResponse savePost(PostCreateRequest postCreateRequest) {
        User user = userService.getUserById(postCreateRequest.getUserId());
        if (user == null) {
            return null;
        } else {
            Post post = new Post();
            post.setTitle(postCreateRequest.getTitle());
            post.setText(postCreateRequest.getText());
            post.setUser(user);
            post.setCreateDate(new Date());
            return new PostResponse(postRepository.save(post), getLikesForPost(post.getId()));
        }
    }

    public PostResponse updatePost(long id, PostUpdateRequest postUpdateRequest) {
        Optional<Post> dbPost = postRepository.findById(id);
        if (dbPost.isPresent()) {
            Post updatedPost = dbPost.get();
            updatedPost.setTitle(postUpdateRequest.getTitle());
            updatedPost.setText(postUpdateRequest.getText());
            postRepository.save(updatedPost);
            return new PostResponse(updatedPost, getLikesForPost(updatedPost.getId()));
        } else {
            return null;
        }
    }

    public void deletePostById(long id) {
        try {
            postRepository.deleteById(id);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    private List<LikeResponse> getLikesForPost(long postId) {
        return likeRepository.findByPostId(postId)
                .stream()
                .map(LikeResponse::new)
                .collect(Collectors.toList());
    }
}
