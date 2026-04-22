package com.example.schedulemanagementdevelop.schedule.entity;

import com.example.schedulemanagementdevelop.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;


    // User가 있어야 Schedule도 존재할 수 있다.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)    // optional 불가
    @JoinColumn(name = "user_id", nullable = false) // nullable 불가
    private User user;

    public Schedule(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void modifyTitle(String title) {
        this.title = title;
    }
}
