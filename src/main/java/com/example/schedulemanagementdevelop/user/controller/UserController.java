package com.example.schedulemanagementdevelop.user.controller;

import com.example.schedulemanagementdevelop.user.dto.*;
import com.example.schedulemanagementdevelop.user.service.UserService;
import jakarta.servlet.http.HttpSession;
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
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUserRequest request, HttpSession session) {
        // 이 당시에는 클라이언트에게 JSESSIONID가 없으므로, WAS(Tomcat)이 자동적으로 새 새션을 생성한다.
        SessionUser sessionUser = userService.login(request);
        session.setAttribute("loginUser", sessionUser);

        // ResponseEntity를 반환할 때, WAS(Tomcat)이 세션 상태를 확인하고 필요 시 자동적으로 JSESSIONID를 Set-Cookie 헤더로 자동 추가해서 반환해 준다.
        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
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

    // 유저 수정 - 회원정보 수정
    @PatchMapping("/users/{userId}")
    public ResponseEntity<ModifyUserResponse> modifyUser(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long userId,
            @RequestBody ModifyUserRequest request
    ) {
        if(sessionUser == null) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.modify(userId, request));
    }

    // 유저 삭제 - 회원 탈퇴
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long userId) {
        if(sessionUser == null) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 유저 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, HttpSession session) {
        // @SessionAttribute 어노테이션을 이용해서 "loginUser" 상태의 sessionUser를 받아 오는 이유는 해당 요청을 보내온 유저의 로그인 상태를 확인하기 위해서이고,
        // 실제 로그아웃은 클라이언트가 보낸 JSESSIONID를 통해 서버가 세션을 식별하고, → 해당 과정은 Controller에 들어오기 전에 WAS(Tomcat)이 자동적으로 처리해줌.
        // 해당 HttpSession을 invalidate하여 로그아웃 처리함
        if (sessionUser == null) {
            return ResponseEntity.badRequest().body("로그인 되어 있지 않습니다.");
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("로그아웃 되었습니다.");
    }
}
