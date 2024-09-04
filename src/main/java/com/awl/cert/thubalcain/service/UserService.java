package com.awl.cert.thubalcain.service;

import com.awl.cert.thubalcain.controller.dto.ViewCreateUser;
import com.awl.cert.thubalcain.controller.dto.ViewDeleteUser;
import com.awl.cert.thubalcain.controller.dto.ViewUpdateUser;

public interface UserService {
    ViewCreateUser.Response createUser(ViewCreateUser.Request request);
    ViewUpdateUser.Response updateUser(ViewUpdateUser.Request request);
    void deleteUser(ViewDeleteUser.Request request);
}
