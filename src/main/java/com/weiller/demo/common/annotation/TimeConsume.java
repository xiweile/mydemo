package com.weiller.demo.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeConsume {

	String value() default "方法";
}
