package com.example.schedulemanagementdevelop.comment.controller;

import com.example.schedulemanagementdevelop.comment.dto.CreateCommentRequest;
import com.example.schedulemanagementdevelop.comment.dto.CreateCommentResponse;
import com.example.schedulemanagementdevelop.comment.dto.GetOneCommentResponse;
import com.example.schedulemanagementdevelop.comment.service.CommentService;
import com.example.schedulemanagementdevelop.user.dto.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(sessionUser, scheduleId, request));
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<GetOneCommentResponse> getOneComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findOne(sessionUser, commentId));
    }
}
