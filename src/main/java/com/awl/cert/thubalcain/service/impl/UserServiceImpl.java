package com.awl.cert.thubalcain.service.impl;

import com.awl.cert.thubalcain.controller.dto.*;
import com.awl.cert.thubalcain.domain.entity.User;
import com.awl.cert.thubalcain.repository.jpa.UserJpaRepository;
import com.awl.cert.thubalcain.service.UserService;
import com.awl.cert.thubalcain.service.mapper.CreateUserMapper;
import com.awl.cert.thubalcain.service.mapper.DeleteUserMapper;
import com.awl.cert.thubalcain.service.mapper.ReadUserMapper;
import com.awl.cert.thubalcain.service.mapper.UpdateUserMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.awl.cert.thubalcain.common.ErrorCode.FAILED_CREATE_USER_DB;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final EntityManager em;

    private final UserJpaRepository userJpaRepository;

    private final CreateUserMapper createUserMapper;
    private final UpdateUserMapper updateUserMapper;
    private final DeleteUserMapper deleteUserMapper;
    private final ReadUserMapper readUserMapper;

    @Override
    public ViewCreateUser.Response createUser(ViewCreateUser.Request request) {
        try {
            User createUser = createUserMapper.toViewCreateUserEntity(request);

            userJpaRepository.save(createUser);

            return createUserMapper.toViewCreateUserResponse(request);
        } catch (Exception e) {
            log.error("error save user to database: {}", e.getCause(), e);
            throw new RuntimeException(FAILED_CREATE_USER_DB.getReason());
        }
    }

    @Override
    @Transactional
    public ViewUpdateUser.Response updateUser(ViewUpdateUser.Request request) {
        userJpaRepository.findById(request.getSeqUser())
                .orElseThrow(() -> new RuntimeException("변경할 사용자 ID를 찾을 수 없습니다."));

        User updateUser = em.find(User.class, request.getSeqUser());
        updateUserMapper.toViewUpdateUserEntity(updateUser, request);

        return updateUserMapper.toViewUpdateUserResponse(updateUser);
    }

    @Override
    public void deleteUser(ViewDeleteUser.Request request) {
        userJpaRepository.findById(request.getSeqUser())
                .orElseThrow(() -> new RuntimeException("삭제할 사용자 ID를 찾을 수 없습니다."));

        User deleteUser = deleteUserMapper.toViewDeleteUserEntity(request);

        userJpaRepository.delete(deleteUser);
    }

    @Override
    public ViewReadUser.Response getUserById(Long id) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저 ID로 데이터를 찾을 수 없습니다."));

        return readUserMapper.toViewUserByIdResponse(user);
    }

    @Override
    public ViewReadAllUser.Response getAllUsers() {
        List<User> users = userJpaRepository.findAll();

        return readUserMapper.toViewAllUsers(users);
    }
}
