package com.awl.cert.thubalcain.service.mapper;

import com.awl.cert.thubalcain.controller.dto.ViewReadAllUser;
import com.awl.cert.thubalcain.controller.dto.ViewReadUser;
import com.awl.cert.thubalcain.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReadUserMapper {

    public ViewReadUser.Response toViewUserByIdResponse(User user) {
        return ViewReadUser.Response.builder()
                .email(user.getEmail())
                .userType(user.getUserType())
                .createDtm(user.getCreateDtm())
                .updateDtm(user.getUpdateDtm())
                .build();
    }

    public ViewReadAllUser.Response toViewAllUsers(List<User> users) {
        return ViewReadAllUser.Response.builder()
                .users(users)
                .build();
    }
}
