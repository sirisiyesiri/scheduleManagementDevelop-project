package com.example.schedulemanagementdevelop.schedule.controller;

import com.example.schedulemanagementdevelop.ExceptionHandler.AuthenticationRequiredException;
import com.example.schedulemanagementdevelop.schedule.dto.*;
import com.example.schedulemanagementdevelop.schedule.service.ScheduleService;
import com.example.schedulemanagementdevelop.user.dto.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users/{userId}/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 추가
    @PostMapping()
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long userId,
            @Valid @RequestBody CreateScheduleRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(sessionUser, userId, request));
    }

    // 일정 단건 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId) {

        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(sessionUser, scheduleId));
    }

    // 일정 전체 조회
    @GetMapping()
    public ResponseEntity<List<GetAllScheduleResponse>> getAllSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestParam(required = false) String userName) {

        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll(sessionUser, userName));
    }

    // 일정 수정
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ModifyScheduleResponse> modifySchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody ModifyScheduleRequest request
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.modify(sessionUser, scheduleId, request));
    }

    // 일정 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId) {

        scheduleService.delete(sessionUser, scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
