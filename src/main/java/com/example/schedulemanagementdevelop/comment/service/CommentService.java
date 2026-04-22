package com.example.schedulemanagementdevelop.comment.service;

import com.example.schedulemanagementdevelop.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
}
