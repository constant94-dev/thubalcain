package com.awl.cert.thubalcain.controller.dto;

import com.awl.cert.thubalcain.domain.entity.UserType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewReadUser {

    @Getter
    @Builder
    public static class Response {
        private String email;
        private UserType userType;
        private LocalDateTime createDtm;
        private LocalDateTime updateDtm;
    }
}
