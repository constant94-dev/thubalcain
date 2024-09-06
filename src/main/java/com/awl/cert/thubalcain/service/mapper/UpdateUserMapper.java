package com.awl.cert.thubalcain.service.mapper;

import com.awl.cert.thubalcain.controller.dto.ViewUpdateUser;
import com.awl.cert.thubalcain.domain.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UpdateUserMapper {
    public void toViewUpdateUserEntity(User updateUser, ViewUpdateUser.Request request) {
        LocalDateTime nowTime = LocalDateTime.now().withNano(0);

        updateUser.changeEmail(request.getEmail());
        updateUser.changePassword(request.getPassword());
        updateUser.changeUserType(request.getUserType());
        updateUser.changeUpdateDtm(nowTime);
    }

    public ViewUpdateUser.Response toViewUpdateUserResponse(User updateUser) {
        return ViewUpdateUser.Response.builder()
                .email(updateUser.getEmail())
                .userType(updateUser.getUserType())
                .updateDtm(updateUser.getUpdateDtm())
                .build();
    }
}
