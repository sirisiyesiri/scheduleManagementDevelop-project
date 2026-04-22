package com.example.schedulemanagementdevelop.ExceptionHandler;

import org.springframework.http.HttpStatus;

public class AuthenticationRequiredException extends ServiceException{

    public AuthenticationRequiredException() {
        super(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다.");
    }
}
