package com.awl.cert.thubalcain.service.mapper;

import com.awl.cert.thubalcain.controller.dto.ViewUpdateUser;
import com.awl.cert.thubalcain.domain.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UpdateUserMapper {
    public User toViewUpdateUserEntity(ViewUpdateUser.Request request) {
        LocalDateTime nowTime = LocalDateTime.now().withNano(0);
        request.changeUpdateDtm(nowTime);

        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .userType(request.getUserType())
                .updateDtm(nowTime)
                .build();
    }

    public ViewUpdateUser.Response toViewUpdateUserResponse(ViewUpdateUser.Request request) {
        return ViewUpdateUser.Response.builder()
                .email(request.getEmail())
                .userType(request.getUserType())
                .updateDtm(request.getUpdateDtm())
                .build();
    }
}
