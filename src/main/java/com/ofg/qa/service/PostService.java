package com.ofg.qa.service;

import com.ofg.qa.dao.PostRepository;
import com.ofg.qa.entity.Post;
import com.ofg.qa.entity.User;
import com.ofg.qa.requests.PostCreateRequest;
import com.ofg.qa.requests.PostUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getPosts(Optional<Long> userId) {
        if (userId.isPresent()) {
            return postRepository.findByUserId(userId.get());
        } else {
            return postRepository.findAll();
        }
    }

    public Optional<Post> getPostById(long id) {
        System.out.println("log");
        return postRepository.findById(id);
    }

    public Post savePost(PostCreateRequest postCreateRequest) {
        User user = userService.getUserById(postCreateRequest.getUserId());
        if (user == null) {
            return null;
        } else {
            Post post = new Post();
            post.setId(postCreateRequest.getId());
            post.setTitle(postCreateRequest.getTitle());
            post.setText(postCreateRequest.getText());
            post.setUser(user);
            post.setCreateDate(new Date());
            return postRepository.save(post);
        }
    }

    public Post updatePost(long id,  PostUpdateRequest postUpdateRequest) {
        Optional<Post> dbPost = postRepository.findById(id);
        if (dbPost.isPresent()) {
            Post updatedPost = dbPost.get();
            updatedPost.setTitle(postUpdateRequest.getTitle());
            updatedPost.setText(postUpdateRequest.getText());
            postRepository.save(updatedPost);
            return updatedPost;
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
}
