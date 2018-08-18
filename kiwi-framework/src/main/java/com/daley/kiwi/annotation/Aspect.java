package com.daley.kiwi.annotation;

import java.lang.annotation.*;

/**
 * @author daley
 * @date 2018/8/18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
