package com.weiller.demo.api.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MyCustomService {

    public Map info(){
        Map<String,String> map  = new HashMap<>();
        map.put("ClassName","MyCustomService");
        map.put("MethodName","info");
        return map;
    }
}
