package com.example.schedulemanagementdevelop.schedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateScheduleResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String authorName;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
