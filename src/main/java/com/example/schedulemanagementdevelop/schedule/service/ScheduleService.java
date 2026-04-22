package com.example.schedulemanagementdevelop.schedule.service;

import com.example.schedulemanagementdevelop.ExceptionHandler.AuthenticationRequiredException;
import com.example.schedulemanagementdevelop.ExceptionHandler.ForbiddenException;
import com.example.schedulemanagementdevelop.ExceptionHandler.NotExistSchedule;
import com.example.schedulemanagementdevelop.ExceptionHandler.NotExistUser;
import com.example.schedulemanagementdevelop.schedule.dto.*;
import com.example.schedulemanagementdevelop.schedule.entity.Schedule;
import com.example.schedulemanagementdevelop.schedule.repository.ScheduleRepository;
import com.example.schedulemanagementdevelop.user.dto.SessionUser;
import com.example.schedulemanagementdevelop.user.entity.User;
import com.example.schedulemanagementdevelop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponse save(SessionUser sessionUser, CreateScheduleRequest request) {

        if(sessionUser == null) {   // 로그인 상태 확인
            throw new AuthenticationRequiredException();
        }

        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                NotExistUser::new   // 유저 존재하지 않을 시에 예외 처리
        );

        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                user
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getUser().getUserName(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetOneScheduleResponse getOne(SessionUser sessionUser, Long scheduleId) {
//        boolean existence = userRepository.existsById(userId);
//        if(!existence) {
//            throw new NotExistUser();
//        }
        // 이미 로그인 상태를 확인 했으므로, 여기서 유저의 존재를 한 번 더 확인하는 것은 불필요하다고 판단

        if(sessionUser == null) {   // 로그인 상태 확인
            throw new AuthenticationRequiredException();
        }

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                NotExistSchedule::new
        );

        return new GetOneScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getUserName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetAllScheduleResponse> getAll(SessionUser sessionUser, String userName) {
//        boolean existence = userRepository.existsById(userId);
//        if(!existence) {
//            throw new NotExistUser();
//        }
        // 이미 로그인 상태를 확인 했으므로, 여기서 유저의 존재를 한 번 더 확인하는 것은 불필요하다고 판단

        if(sessionUser == null) {   // 로그인 상태 확인
            throw new AuthenticationRequiredException();
        }

        List<Schedule> schedules;

        if(userName != null) {  // 파라미터 존재 시 userName기준 전체 조회
            schedules = scheduleRepository.findAllByUserName(userName);

            return schedules.stream()
                    .map(schedule -> new GetAllScheduleResponse(
                            schedule.getId(),
                            schedule.getTitle(),
                            schedule.getContent(),
                            schedule.getUser().getUserName(),
                            schedule.getCreatedAt(),
                            schedule.getModifiedAt()
                    )).toList();
        }

        schedules = scheduleRepository.findAllActiveUserSchedules();

        return schedules.stream()
                .map(schedule -> new GetAllScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getUser().getUserName(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                )).toList();
    }

    @Transactional
    public ModifyScheduleResponse modify(SessionUser sessionUser, Long scheduleId, ModifyScheduleRequest request) {

        if(sessionUser == null) {   // 로그인 상태 확인
            throw new AuthenticationRequiredException();
        }

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                NotExistSchedule::new
        );

        if(!schedule.getUser().getId().equals(sessionUser.getId())) {   // 본인이 작성한 일정이 아니면 수정, 삭제 불가
            throw new ForbiddenException();
        }

        schedule.modifyTitle(request.getTitle());

        return new ModifyScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getUserName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(SessionUser sessionUser, Long scheduleId) {
        if(sessionUser == null) {   // 로그인 상태 확인
            throw new AuthenticationRequiredException();
        }

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                NotExistSchedule::new
        );

        if(!schedule.getUser().getId().equals(sessionUser.getId())) {   // 본인이 작성한 일정이 아니면 수정, 삭제 불가
            throw new ForbiddenException();
        }

        scheduleRepository.deleteById(scheduleId);
    }
}
