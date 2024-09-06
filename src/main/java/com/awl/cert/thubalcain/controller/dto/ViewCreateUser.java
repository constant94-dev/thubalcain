package com.awl.cert.thubalcain.controller.dto;

import com.awl.cert.thubalcain.domain.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewCreateUser {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @Email(message = "이메일 형식을 확인해주세요")
        private String email;
        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;
        @NotNull(message = "유저 타입 입력은 필수 입니다.")
        private UserType userType;
        private LocalDateTime createDtm;
        private LocalDateTime updateDtm;

        public void changeCreateDtm(LocalDateTime createDtm) {
            this.createDtm = createDtm;
        }

        public void changeUpdateDtm(LocalDateTime updateDtm) {
            this.updateDtm = updateDtm;
        }
    }

    @Getter
    @Builder
    public static class Response {
        private String email;
        private UserType userType;
        private LocalDateTime createDtm;
        private LocalDateTime updateDtm;
    }

}
