package com.stars.starsspring.framework.stereotype;

import java.lang.annotation.*;

/**
 * 组件——注解
 *
 * @author stars
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";
}
