package com.awl.cert.thubalcain.controller.dto;

import com.awl.cert.thubalcain.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewReadAllUser {

    @Getter
    @Builder
    public static class Response {
        private List<User> users;
    }
}
