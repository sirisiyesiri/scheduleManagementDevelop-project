package com.example.schedulemanagementdevelop.user.controller;

import com.example.schedulemanagementdevelop.user.dto.*;
import com.example.schedulemanagementdevelop.user.service.UserService;
import jakarta.validation.Valid;
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

    // 유저 추가 기능 = 회원가입 기능
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    // 유저 로그인 기능
    @PostMapping("/logins")
    public ResponseEntity<LoginUserResponse> login(@Valid @RequestBody LoginUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body();
    }

    // 유저 단건 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetOneUserResponse> getOneUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    // 유저 전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<GetAllUserResponse>> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    // 유저 수정
    @PatchMapping("/users/{userId}")
    public ResponseEntity<ModifyUserResponse> modifyUser(
            @PathVariable Long userId,
            @RequestBody ModifyUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.modify(userId, request));
    }

    // 유저 삭제
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
