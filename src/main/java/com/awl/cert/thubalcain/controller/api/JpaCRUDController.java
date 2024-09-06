package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.common.response.ApiResponse;
import com.awl.cert.thubalcain.controller.dto.*;
import com.awl.cert.thubalcain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jpa")
@RequiredArgsConstructor
public class JpaCRUDController {
    private final UserService userService;

    @GetMapping("/user/all")
    public ResponseEntity<ApiResponse<ViewReadAllUser.Response>> viewAllUsers() {
        ViewReadAllUser.Response response = userService.getAllUsers();
        return new ResponseEntity<>(ApiResponse.success("모든 사용자 데이터를 불러왔습니다.", response), HttpStatus.OK);
    }

    @GetMapping("/user/{seqUser}")
    public ResponseEntity<ApiResponse<ViewReadUser.Response>> viewUserById(@PathVariable(name = "seqUser") Long seqUser) {
        ViewReadUser.Response response = userService.getUserById(seqUser);
        return new ResponseEntity<>(ApiResponse.success("지정한 사용자 데이터를 불러왔습니다.", response), HttpStatus.OK);
    }


    @PostMapping("/user/create")
    public ResponseEntity<ApiResponse<ViewCreateUser.Response>> viewCreateUser(@RequestBody @Valid ViewCreateUser.Request request) {
        ViewCreateUser.Response response = userService.createUser(request);
        return new ResponseEntity<>(ApiResponse.success("사용자가 생성 되었습니다.", response), HttpStatus.OK);
    }

    @PutMapping("/user/update")
    public ResponseEntity<ApiResponse<ViewUpdateUser.Response>> viewUpdateUser(@RequestBody @Valid ViewUpdateUser.Request request) {
        ViewUpdateUser.Response response = userService.updateUser(request);
        return new ResponseEntity<>(ApiResponse.success("사용자 정보가 업데이트 되었습니다.", response), HttpStatus.OK);
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<ApiResponse<String>> viewDeleteUser(@RequestBody @Valid ViewDeleteUser.Request request) {
        userService.deleteUser(request);
        return new ResponseEntity<>(ApiResponse.success("사용자가 삭제 되었습니다.", null), HttpStatus.OK);
    }
}
