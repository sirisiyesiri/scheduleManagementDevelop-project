package com.example.schedulemanagementdevelop.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 10, message = "제목은 {max}자 이내여야 합니다.")
    private String title;
    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;

}
