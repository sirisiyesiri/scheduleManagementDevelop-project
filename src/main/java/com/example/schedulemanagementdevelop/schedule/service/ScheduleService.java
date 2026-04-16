package com.example.schedulemanagementdevelop.schedule.service;

import com.example.schedulemanagementdevelop.schedule.dto.*;
import com.example.schedulemanagementdevelop.schedule.entity.Schedule;
import com.example.schedulemanagementdevelop.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getAuthorName()
//                request.getPassword()
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getAuthorName(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetOneScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );

        return new GetOneScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthorName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetAllScheduleResponse> getAll(String authorName) {
        List<Schedule> schedules = new ArrayList<>();

        if(authorName != null) {
            schedules = scheduleRepository.findAllByAuthorNameOrderByModifiedAtDesc(authorName);

            return schedules.stream()
                    .map(schedule -> new GetAllScheduleResponse(
                            schedule.getId(),
                            schedule.getTitle(),
                            schedule.getContent(),
                            schedule.getAuthorName(),
                            schedule.getCreatedAt(),
                            schedule.getModifiedAt()
                    )).toList();
        }

        schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();

        return schedules.stream()
                .map(schedule -> new GetAllScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getAuthorName(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                )).toList();
    }

    @Transactional
    public ModifyScheduleResponse modify(Long scheduleId, CreateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );

//        if(!schedule.getPassword().equals(request.getPassword())) {
//            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
//        }

        schedule.modifyInfo(request.getTitle(), request.getAuthorName());

        return new ModifyScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthorName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
