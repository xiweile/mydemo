package com.weiller.demo.utils;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApplicationContextHelper implements ApplicationContextAware{

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext=applicationContext;
    }

    /**
     * 根据bean的id来查找对象
     *
     * @param name bean名称
     * @return bean
     */
    public static <T> T getBeanByName(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据bean的class来查找对象
     *
     * @param c class对象
     * @return bean
     */
    public static <T> T getBeanByClass(Class c) {
        assertContextInjected();
        return (T) applicationContext.getBean(c);
    }

    /**
     * 根据bean的class来查找所有的对象(包括子类)
     *
     * @param c class对象
     * @return bean Map
     */
    public static Map getBeansByClass(Class c) {
        assertContextInjected();
        return applicationContext.getBeansOfType(c);
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        Validate.validState(applicationContext != null,
                "applicaitonContext属性未注入, 请将ApplicationContextHelper交由Spring管理");
    }
}
