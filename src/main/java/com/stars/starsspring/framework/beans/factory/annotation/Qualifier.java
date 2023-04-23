package com.stars.starsspring.framework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * 修饰符——注解
 *
 * @author stars
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {

    String value() default "";
}
