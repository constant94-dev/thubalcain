package com.awl.cert.thubalcain.service.mapper;

import com.awl.cert.thubalcain.controller.dto.ViewCreateUser;
import com.awl.cert.thubalcain.domain.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateUserMapper {
    /* 사용자 생성 응답 값 Mapper */
    public ViewCreateUser.Response toViewCreateUserResponse(ViewCreateUser.Request request) {
        return ViewCreateUser.Response.builder()
                .email(request.getEmail())
                .userType(request.getUserType())
                .createDtm(request.getCreateDtm())
                .updateDtm(request.getUpdateDtm())
                .build();
    }

    /* 사용자 생성 Entity Mapper */
    public User toViewCreateUserEntity(ViewCreateUser.Request request) {
        LocalDateTime nowTime = LocalDateTime.now().withNano(0);
        request.changeCreateDtm(nowTime);
        request.changeUpdateDtm(nowTime);

        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .userType(request.getUserType())
                .createDtm(nowTime)
                .updateDtm(nowTime)
                .build();
    }
}
