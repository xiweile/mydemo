package com.weiller.demo.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weiller.demo.api.service.DynamicApiService;
import com.weiller.demo.common.annotation.ValidateOnAccess;
import com.weiller.demo.common.rest.client.FtcspRestClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class DynamicApi {

    @Autowired
    DynamicApiService dynamicApiService;

    @ValidateOnAccess
    @PostMapping(value="{level}/{service}/{method}" , produces="application/json;charset=UTF-8")
    public Object apiHandler(@PathVariable("level") String level,
                             @PathVariable("service") String service,
                             @PathVariable("method") String method,
                             @RequestParam("appId") String appId,
                             @RequestParam("sessionId") String sessionId,
                             @RequestParam("requestId") String requestId,
                             @RequestParam("timestamp") String timestamp,
                             @RequestParam("sign") String sign,
                             @RequestBody String data,
                             String aid){

        log.info("接口请求参数列表：\n level:{}\n service:{}\n method:{}\n" +
                        " appId:{}\n sessionId:{}\n requestId:{}\n timestamp:{}\n sign:{}\ndata:{}\n aid:{}"
        ,level,service,method,appId,sessionId,requestId,timestamp,sign,data,aid);

        return dynamicApiService.fetchData(aid) ;
    }

}
