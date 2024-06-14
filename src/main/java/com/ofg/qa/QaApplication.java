package com.ofg.qa;

import com.ofg.qa.dao.CommentRepository;
import com.ofg.qa.dao.LikeRepository;
import com.ofg.qa.dao.PostRepository;
import com.ofg.qa.dao.UserRepository;
import com.ofg.qa.entity.Comment;
import com.ofg.qa.entity.Like;
import com.ofg.qa.entity.Post;
import com.ofg.qa.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class QaApplication {

    public static void main(String[] args) {
        SpringApplication.run(QaApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, LikeRepository likeRepository) {
        return (args) -> {
            for (var i = 1; i <= 3; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setPassword("P4ssword");
                userRepository.save(user);

                Post post = new Post();
                post.setUser(user);
                post.setTitle("title" + i);
                post.setText("post" + i);
                post.setCreateDate(new Date());
                postRepository.save(post);

                Comment comment = new Comment();
                comment.setUser(user);
                comment.setPost(post);
                comment.setText("comment" + i);
                comment.setCreateDate(new Date());
                commentRepository.save(comment);

                Like like = new Like();
                like.setUser(user);
                like.setPost(post);
                likeRepository.save(like);
            }
        };
    }
}
