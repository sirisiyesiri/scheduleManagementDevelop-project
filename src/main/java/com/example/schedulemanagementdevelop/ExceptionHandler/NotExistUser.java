package com.example.schedulemanagementdevelop.ExceptionHandler;

import org.springframework.http.HttpStatus;

public class NotExistUser extends ServiceException{

    public NotExistUser() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");
    }
}
