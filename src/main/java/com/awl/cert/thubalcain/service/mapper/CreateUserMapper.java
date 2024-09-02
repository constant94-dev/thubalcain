package com.awl.cert.thubalcain.service.mapper;

import com.awl.cert.thubalcain.common.ErrorCode;
import com.awl.cert.thubalcain.controller.dto.request.ViewCreateUser;
import org.springframework.stereotype.Component;

@Component
public class CreateUserMapper {
    public ViewCreateUser.Response toViewCreateUserResponse(ViewCreateUser.Request request) {
        return ViewCreateUser.Response.builder()
                .email(request.getEmail())
                .userType(request.getUserType())
                .createDtm(request.getCreateDtm())
                .updateDtm(request.getUpdateDtm())
                .build();
    }

    public ViewCreateUser.Response toViewCreateUserErrorResponse() {
        return ViewCreateUser.Response.builder()
                .message(ErrorCode.NOT_USER_DB_CREATE.getReason())
                .build();
    }
}
