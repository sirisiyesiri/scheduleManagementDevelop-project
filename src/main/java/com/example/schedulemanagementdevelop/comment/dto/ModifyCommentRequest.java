package com.example.schedulemanagementdevelop.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ModifyCommentRequest {

    @NotBlank
    private String content;
}
