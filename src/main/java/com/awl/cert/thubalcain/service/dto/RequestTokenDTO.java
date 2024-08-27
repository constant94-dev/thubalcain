package com.awl.cert.thubalcain.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class RequestTokenDTO {

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        @Email(message = "이메일 문제 있어")
        private String email;
        @NotBlank(message = "이름 문제 있어")
        private String name;
        @NotBlank(message = "대상 문제 있어")
        private String aud;
        @NotBlank(message = "인가 코드 문제 있어")
        private String authCode;

        public String updateAuthCode(String authCode) {
            this.authCode = authCode;
            return authCode;
        }
    }

    protected RequestTokenDTO(){}
}
