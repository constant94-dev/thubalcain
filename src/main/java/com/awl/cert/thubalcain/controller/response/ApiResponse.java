package com.awl.cert.thubalcain.controller.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponse<T> {
    private String message;
    private T data;

    ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null);
    }
}
