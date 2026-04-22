package com.example.schedulemanagementdevelop.ExceptionHandler;

import org.springframework.http.HttpStatus;

public class NotExistSchedule extends ServiceException{

    public NotExistSchedule() {
        super(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다.");
    }
}
