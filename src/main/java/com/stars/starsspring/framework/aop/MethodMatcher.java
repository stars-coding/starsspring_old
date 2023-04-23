package com.stars.starsspring.framework.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配器——接口
 * 通常与切点对象一起使用，以确定在哪些方法上应用通知。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * matches
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface MethodMatcher {

    /**
     * 匹配（方法对象，目标类对象）
     * 检查给定的目标方法是否匹配特定的规则。
     *
     * @param method      要匹配的目标方法对象
     * @param targetClass 目标类对象
     * @return 如果方法匹配规则，则返回true；否则返回false
     */
    boolean matches(Method method, Class<?> targetClass);
}
