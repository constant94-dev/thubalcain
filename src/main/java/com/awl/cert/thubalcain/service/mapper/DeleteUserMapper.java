package com.awl.cert.thubalcain.service.mapper;

import com.awl.cert.thubalcain.controller.dto.ViewDeleteUser;
import com.awl.cert.thubalcain.domain.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DeleteUserMapper {
    public User toViewDeleteUserEntity(ViewDeleteUser.Request request) {
        LocalDateTime nowTime = LocalDateTime.now().withNano(0);

        return User.builder()
                .seqUser(request.getSeqUser())
                .deleteDtm(nowTime)
                .build();
    }
}
