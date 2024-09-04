package com.awl.cert.thubalcain.service.impl;

import com.awl.cert.thubalcain.controller.dto.ViewCreateUser;
import com.awl.cert.thubalcain.controller.dto.ViewDeleteUser;
import com.awl.cert.thubalcain.controller.dto.ViewUpdateUser;
import com.awl.cert.thubalcain.domain.entity.User;
import com.awl.cert.thubalcain.repository.jpa.UserJpaRepository;
import com.awl.cert.thubalcain.service.UserService;
import com.awl.cert.thubalcain.service.mapper.CreateUserMapper;
import com.awl.cert.thubalcain.service.mapper.DeleteUserMapper;
import com.awl.cert.thubalcain.service.mapper.UpdateUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.awl.cert.thubalcain.common.ErrorCode.FAILED_CREATE_USER_DB;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;

    private final CreateUserMapper createUserMapper;
    private final UpdateUserMapper updateUserMapper;
    private final DeleteUserMapper deleteUserMapper;

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
    public ViewUpdateUser.Response updateUser(ViewUpdateUser.Request request) {
        userJpaRepository.findById(request.getSeqUser())
                .orElseThrow(() -> new RuntimeException("변경할 사용자 ID를 찾을 수 없습니다."));

        User updateUser = updateUserMapper.toViewUpdateUserEntity(request);

        userJpaRepository.save(updateUser);

        return updateUserMapper.toViewUpdateUserResponse(request);
    }

    @Override
    public void deleteUser(ViewDeleteUser.Request request) {
        userJpaRepository.findById(request.getSeqUser())
                .orElseThrow(() -> new RuntimeException("삭제할 사용자 ID를 찾을 수 없습니다."));

        User deleteUser = deleteUserMapper.toViewDeleteUserEntity(request);

        userJpaRepository.delete(deleteUser);
    }
}
