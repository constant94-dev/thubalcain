package com.awl.cert.thubalcain.controller.api.dto;

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
        @Email(message = "이메일 형식을 확인해주세요")
        private String email;
        @NotBlank(message = "이름을 입력해주세요")
        private String name;
        @NotBlank(message = "대상을 입력해주세요")
        private String aud;
        @NotBlank(message = "인가코드를 입력해주세요")
        private String authCode;

        public String updateAuthCode(String authCode) {
            this.authCode = authCode;
            return authCode;
        }
    }

    protected RequestTokenDTO(){}
}
