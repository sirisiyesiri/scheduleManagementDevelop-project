package com.example.schedulemanagementdevelop.ExceptionHandler;

import org.springframework.http.HttpStatus;

public class AlreadyLoggedInException extends ServiceException{

    public AlreadyLoggedInException() {
        super(HttpStatus.CONFLICT, "이미 회원가입이 된 상태입니다.");    // 로그인이 되었다는 건 이미 회원가입이 되었다는 거니까
    }
}
