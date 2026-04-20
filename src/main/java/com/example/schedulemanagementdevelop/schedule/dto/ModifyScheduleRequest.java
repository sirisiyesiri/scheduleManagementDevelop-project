package com.example.schedulemanagementdevelop.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ModifyScheduleRequest {

    private String title;

//    @NotBlank(message = "비밀번호를 입력해주셔야합니다.")
//    private String password;
}
