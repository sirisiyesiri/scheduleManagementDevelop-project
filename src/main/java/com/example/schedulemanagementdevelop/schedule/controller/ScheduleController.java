package com.example.schedulemanagementdevelop.schedule.controller;

import com.example.schedulemanagementdevelop.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
}
