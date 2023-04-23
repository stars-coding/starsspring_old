package com.stars.starsspring.framework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * 值——注解
 *
 * @author stars
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    String value();
}
