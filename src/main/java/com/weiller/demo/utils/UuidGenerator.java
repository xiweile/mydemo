package com.weiller.demo.utils;

import java.util.UUID;

public class UuidGenerator {

    public static String getUUID(){
        return  UUID.randomUUID().toString().replaceAll("-","");
    }
}
