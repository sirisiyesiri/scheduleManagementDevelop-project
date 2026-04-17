package com.example.schedulemanagementdevelop.user.controller;

import com.example.schedulemanagementdevelop.user.dto.CreateUserRequest;
import com.example.schedulemanagementdevelop.user.dto.CreateUserResponse;
import com.example.schedulemanagementdevelop.user.dto.GetAllUserResponse;
import com.example.schedulemanagementdevelop.user.dto.GetOneUserResponse;
import com.example.schedulemanagementdevelop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<GetOneUserResponse> getOneUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetAllUserResponse>> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<>
}
