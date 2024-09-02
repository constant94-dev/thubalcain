package com.awl.cert.thubalcain.controller.dto.request;

import com.awl.cert.thubalcain.domain.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewCreateUser {

    @Getter
    @Builder
    public static class Request {
        @Email(message = "이메일 형식을 확인해주세요")
        private String email;
        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;
        @NotNull(message = "유저타입을 입력해주세요")
        private UserType userType;
        private LocalDateTime createDtm;
        private LocalDateTime updateDtm;
    }

    @Builder
    public static class Response {
        private String email;
        private UserType userType;
        private LocalDateTime createDtm;
        private LocalDateTime updateDtm;
        private String message;
    }

}
