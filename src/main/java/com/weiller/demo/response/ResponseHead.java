package com.weiller.demo.response;

import lombok.Data;

@Data
public class ResponseHead {

    private String requestId;

    private String errorCode;

    private String errorMsg;

    /**
     * 当前时间戳毫秒数
     */
    private long timestamp;

    /**
     * 接口处理耗时毫秒数
     */
    private long time;

    private static ResponseHead responseHead;


    public ResponseHead(){}

    public ResponseHead(String requestId,String errorCode, String errorMsg, long timestamp, long time) {
        this.requestId = requestId;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.timestamp = timestamp;
        this.time = time;
    }
    public static ResponseHead build() {
        if(responseHead==null){
            responseHead = new ResponseHead();
        }
        return responseHead;
    }

}
