package com.hua.parambuilder_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hua
 * @version V1.0
 * @date 2018/12/29 10:09
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Builder {

    String staticMethodName() default "";

    String createMethodName() default "";
}
