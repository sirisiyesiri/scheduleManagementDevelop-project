package com.example.schedulemanagementdevelop.comment.controller;

import com.example.schedulemanagementdevelop.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
}
