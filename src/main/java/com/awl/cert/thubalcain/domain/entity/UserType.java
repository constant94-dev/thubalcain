package com.awl.cert.thubalcain.domain.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserType {
    ADMIN("운영자"),
    USER("일반 사용자"),
    SPECIAL("특별 사용자");

    private final String comment;
}
