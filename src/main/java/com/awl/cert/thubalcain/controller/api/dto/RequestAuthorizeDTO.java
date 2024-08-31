package com.awl.cert.thubalcain.controller.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestAuthorizeDTO(
        @Email(message = "이메일을 입력해주세요") String email,
        @NotBlank(message = "비밀번호를 입력해주세요") String password
) {}
