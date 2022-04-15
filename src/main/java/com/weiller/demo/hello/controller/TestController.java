package com.weiller.demo.hello.controller;

import com.weiller.demo.common.id.UID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    @ResponseBody
    @PostMapping("conn")
    public Map<String,String> conn(String msg){
        HashMap<String,String> map = new HashMap<>();
        map.put("ok",msg+"...远程调用返回...");
        return map;
    }

    @ResponseBody
    @GetMapping("uid")
    public Map<String,Long> uid(){
        HashMap<String,Long> map = new HashMap<>();
        map.put("uid",UID.get());
        return map;
    }
}
