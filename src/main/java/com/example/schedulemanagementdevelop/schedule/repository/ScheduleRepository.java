package com.example.schedulemanagementdevelop.schedule.repository;

import com.example.schedulemanagementdevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByOrderByModifiedAtDesc();

    List<Schedule> findAllByUser_NameOrderByModifiedAtDesc(String userName);
}
