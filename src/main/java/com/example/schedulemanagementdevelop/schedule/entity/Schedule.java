package com.example.schedulemanagementdevelop.schedule.entity;

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
    private String authorName;
    private String password;

    public Schedule(String title, String content, String authorName, String password) {
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.password = password;
    }

    public void modifyInfo(String title, String authorName) {
        if(title != null) {
            this.title = title;
        }
        if(authorName != null) {
            this.authorName = authorName;
        }
    }
}
