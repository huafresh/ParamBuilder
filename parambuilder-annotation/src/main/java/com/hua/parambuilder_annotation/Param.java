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
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Param {
    String setterMethodName() default "";

    String javaDoc() default "";
}
