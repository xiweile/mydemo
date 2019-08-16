package com.weiller.demo.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weiller.demo.response.Msg;
import com.weiller.demo.response.ResponseHead;
import com.weiller.demo.utils.DESUtil;
import com.weiller.demo.utils.SignUtils;
import com.weiller.demo.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Aspect
@Order(2)
public class ApiValidateInterceptor {

    Logger logger = LoggerFactory.getLogger(ApiValidateInterceptor.class);


    @Pointcut("@annotation(com.weiller.demo.common.annotation.ValidateOnAccess)")
    public void validate(){

    }

    @Around("validate()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Map<String,Object> localMap = new HashMap<>();
        Long startTime = System.currentTimeMillis();
        localMap.put("startTime", startTime);

        Object result = null;
        String signKey = "";
        String desKey = "";

        // 1.捕获资源信息
        /** 参数列表
            0 level,
            1 service,
            2 method,
            3 appId,
            4 sessionId,
            5 requestId,
            6 timestamp,
            7 sign,
            8 data,
            9 aid
         */
        Object[] args = pjp.getArgs();
        localMap.put("requestId", args[5]);
        try {


            // 2.验签
            // 2.1 有效性校验 时间差最大允许1分钟
            Long timestamp = Long.parseLong((String) args[6]);
            if (System.currentTimeMillis() - timestamp > 10 * 60 * 1000) {
                throw new RuntimeException("请求过期");
            }

            //TODO 2.2 判断是否授权 根据appId查询
            signKey = "abc";
            desKey = "d734bf4d0c4991";//要求14位

            // 2.3 data参数解密
            args[8] = DESUtil.decrypt((String) args[8], desKey);
            // 2.4 合法性校验 验签
            boolean checkreult = SignUtils.checkSign(signKey, (String) args[3], (String) args[4], (String) args[5], (String) args[6], (String) args[7], (String) args[8]);
            if (!checkreult) {
                throw new RuntimeException("签名不合法");
            }

            //TODO 2.4 接口权限校验


            // 3.校验成功后，为参数添加aid的值
            args[args.length - 1] = "aid001";

            result = pjp.proceed(args);//执行方法

            localMap.put("desKey", desKey);
        }catch (Throwable t){
            log.error(t.getMessage(),t);
            throw new RuntimeException(t.getMessage(),t);
        }finally {
            ThreadLocalUtil.add(localMap);
        }


        return result;
    }
}
