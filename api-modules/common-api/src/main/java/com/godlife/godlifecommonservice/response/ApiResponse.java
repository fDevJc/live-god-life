package com.godlife.godlifecommonservice.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    /**
     * 상태 (success / error)
     */
    private String status;

    /**
     * 데이터
     */
    private T data;

    /**
     * 오류 코드
     */
    private Integer code;

    /**
     * 오류 메시지
     */
    private String message;

    /**
     * 성공 Response
     */
    public static <T> ApiResponse<T> successResponse(T data, String message) {
        return new ApiResponse<>("success", data, null, message);
    }

    /**
     * 실패 Response
     */
    public static ApiResponse<?> failResponse(Integer code, String message) {
        return new ApiResponse<>("error", null, code, message);
    }
}