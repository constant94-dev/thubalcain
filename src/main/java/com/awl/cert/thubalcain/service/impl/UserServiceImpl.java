package com.awl.cert.thubalcain.service.impl;

import com.awl.cert.thubalcain.controller.dto.request.ViewCreateUser;
import com.awl.cert.thubalcain.domain.entity.User;
import com.awl.cert.thubalcain.repository.jpa.UserJpaRepository;
import com.awl.cert.thubalcain.service.UserService;
import com.awl.cert.thubalcain.service.mapper.CreateUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;

    private final CreateUserMapper createUserMapper;

    @Override
    public ViewCreateUser.Response createUser(ViewCreateUser.Request request) {
        LocalDateTime nowTime = LocalDateTime.now();

        User createUser = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .userType(request.getUserType())
                .createDtm(nowTime)
                .updateDtm(nowTime)
                .build();

        userJpaRepository.save(createUser);

        return createUserMapper.toViewCreateUserResponse(request);
    }
}
