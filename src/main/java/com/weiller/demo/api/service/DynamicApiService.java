package com.weiller.demo.api.service;

import com.weiller.demo.utils.ApplicationContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Service
public class DynamicApiService {

    public Object fetchData(String aid){
        log.info("准备根据api编号[{}]获取数据",aid);
        // 1.根据aid获取的接口的配置信息

        // 2.从接口类型判断，如果是SQL配置类型，则直接调用相关执行器

        // 3.从接口类型判断，如果是JAVA实体类型，则获取该接口名的spring Bean
        MyCustomService myCustomService = ApplicationContextHelper.getBeanByName("myCustomService");
        Class<? extends MyCustomService> aClass = myCustomService.getClass();
        Object data = null;
        try {
            Method info = aClass.getMethod("info");
            data = info.invoke(myCustomService);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return data;
    }
}
