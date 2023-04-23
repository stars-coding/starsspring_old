package com.stars.starsspring.framework.aop;

import java.lang.reflect.Method;

/**
 * 方法前置通知——接口
 * 可以在目标方法执行之前执行自定义的逻辑。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * before
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    /**
     * 前置（方法对象，方法参数，目标对象）
     * 在目标方法执行之前执行的通知方法。
     *
     * @param method 正在被调用的目标方法对象
     * @param args   传递给目标方法的参数数组
     * @param target 目标对象，即包含目标方法的实例
     * @throws Throwable 如果通知方法遇到异常，则抛出Throwable异常
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
