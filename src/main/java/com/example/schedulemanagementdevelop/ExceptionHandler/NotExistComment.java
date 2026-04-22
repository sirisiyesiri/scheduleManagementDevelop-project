package com.example.schedulemanagementdevelop.ExceptionHandler;

import org.springframework.http.HttpStatus;

public class NotExistComment extends ServiceException{

    public NotExistComment() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다.");
    }
}
