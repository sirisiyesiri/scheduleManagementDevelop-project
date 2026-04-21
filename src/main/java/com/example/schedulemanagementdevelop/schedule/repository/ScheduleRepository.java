package com.example.schedulemanagementdevelop.schedule.repository;

import com.example.schedulemanagementdevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s JOIN FETCH s.user WHERE s.user.isDeleted = false ORDER BY s.modifiedAt DESC")
    List<Schedule> findAllActiveUserSchedules();

    @Query("SELECT s FROM Schedule s JOIN FETCH s.user WHERE s.user.userName = :userName AND s.user.isDeleted = false ORDER BY s.modifiedAt DESC")
    List<Schedule> findAllByUserName(@Param("userName") String userName);
}
