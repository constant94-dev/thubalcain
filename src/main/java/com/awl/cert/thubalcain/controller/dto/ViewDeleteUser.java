package com.awl.cert.thubalcain.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewDeleteUser {

    @Getter
    @Builder
    public static class Request {
        @NotNull(message = "seqUser를 입력해주세요.")
        private Long seqUser;
        private LocalDateTime deleteDtm;
    }

}
