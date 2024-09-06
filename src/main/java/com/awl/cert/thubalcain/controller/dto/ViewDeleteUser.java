package com.awl.cert.thubalcain.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewDeleteUser {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotNull(message = "seqUser를 입력해주세요.")
        private Long seqUser;
        private LocalDateTime deleteDtm;
    }

}
