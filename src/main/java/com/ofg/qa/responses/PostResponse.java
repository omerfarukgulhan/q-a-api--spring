package com.ofg.qa.responses;

import com.ofg.qa.entity.Like;
import com.ofg.qa.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;

    private Long userId;

    private String username;

    private String title;

    private String text;

    private List<LikeResponse> postLikes;

    public PostResponse(Post post, List<LikeResponse> likes) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.text = post.getText();
        this.postLikes = likes;
    }
}
