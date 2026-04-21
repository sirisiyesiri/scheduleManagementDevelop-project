package com.example.schedulemanagementdevelop.schedule.controller;

import com.example.schedulemanagementdevelop.schedule.dto.*;
import com.example.schedulemanagementdevelop.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
            @PathVariable Long userId,
            @RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(userId, request));
    }

    // 일정 단건 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule(
            @PathVariable Long userId,
            @PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(userId, scheduleId));
    }

    // 일정 전체 조회
    @GetMapping()
    public ResponseEntity<List<GetAllScheduleResponse>> getAllSchedule(
            @PathVariable Long userId,
            @RequestParam(required = false) String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll(userId, userName));
    }

    // 일정 수정
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ModifyScheduleResponse> modifySchedule(
            @PathVariable Long scheduleId,
            @Validated @RequestBody CreateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.modify(scheduleId, request));
    }

    // 일정 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.delete(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
