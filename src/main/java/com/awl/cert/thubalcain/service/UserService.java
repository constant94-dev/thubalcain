package com.awl.cert.thubalcain.service;

import com.awl.cert.thubalcain.controller.dto.*;

public interface UserService {
    ViewCreateUser.Response createUser(ViewCreateUser.Request request);
    ViewUpdateUser.Response updateUser(ViewUpdateUser.Request request);
    void deleteUser(ViewDeleteUser.Request request);
    ViewReadUser.Response getUserById(Long id);
    ViewReadAllUser.Response getAllUsers();
}
