package com.weiller.demo.response;

import lombok.Data;

/**
 * 响应消息类
 */
@Data
public class ApiResponse {

    /** 响应头 */
    private Object head;

    /** 响应体 */
    private Object body;

    private static ApiResponse apiResponse;

    public static ApiResponse success(ResponseHead head, Object body) {
        apiResponse = new ApiResponse();
        apiResponse.setHead(head);
        apiResponse.setBody(body);
        return apiResponse;
    }

    public static ApiResponse fail(ResponseHead head) {
        apiResponse = new ApiResponse();
        apiResponse.setHead(head);
        return apiResponse;
    }
}
