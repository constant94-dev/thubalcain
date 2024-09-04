package com.awl.cert.thubalcain.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NO_SUCH_ALGORITHM(500, "Hashing 알고리듬을 찾을 수 없습니다."),
    INVALID_KEY_SPEC(500, "유효하지 않은 KeySpec 입니다."),
    FAILED_CREATE_USER_DB(500, "DB에 사용자가 저장되지 않았습니다.");

    private final int code;
    private final String reason;

    ErrorCode(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
