package com.weiller.demo.common.interceptor;

import com.weiller.demo.response.ApiResponse;
import com.weiller.demo.response.ResponseHead;
import com.weiller.demo.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandleAdvice {

    private final static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandleAdvice.class);

    @ExceptionHandler
    public ApiResponse handler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        Map localMap = ThreadLocalUtil.getMap();
        long startTime = (long)localMap.get("startTime");
        String requestId = (String)localMap.get("requestId");
        logger.info("Restful Http请求发生异常...");

        if (res.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            logger.info("修改返回状态值为200");
            res.setStatus(HttpStatus.OK.value());
        }

        if (e instanceof RuntimeException) {
            logger.error("代码500：" + e.getMessage(), e);
            return ApiResponse.fail(new ResponseHead(requestId,"500",e.getMessage(),startTime,System.currentTimeMillis()-startTime));
        } else {
            logger.error("代码900：" + e.getMessage(), e);
            return ApiResponse.fail(new ResponseHead("123","900","服务器代码发生异常,请联系管理员",startTime,System.currentTimeMillis()-startTime));
        }


    }

}