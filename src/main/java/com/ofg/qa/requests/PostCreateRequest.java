package com.ofg.qa.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequest {
    private String title;

    private String text;

    private Long userId;
}
