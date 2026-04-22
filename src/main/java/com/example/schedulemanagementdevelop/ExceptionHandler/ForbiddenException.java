package com.example.schedulemanagementdevelop.ExceptionHandler;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ServiceException{

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, "본인이 작성한 항목만 수정 또는 삭제할 수 있습니다.");
    }
}
