package com.ofg.qa.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {
    private Long userId;

    private Long postId;

    private String text;
}
