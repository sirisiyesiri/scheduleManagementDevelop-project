package com.example.schedulemanagementdevelop.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ModifyUserRequest {

    @Size(max = 8, message = "사용자 이름은 {max}자 이내여야합니다.")
    private String userName;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
}
