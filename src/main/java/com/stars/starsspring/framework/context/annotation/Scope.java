package com.stars.starsspring.framework.context.annotation;

import java.lang.annotation.*;

/**
 * 范围——注解
 *
 * @author stars
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default "singleton";
}
