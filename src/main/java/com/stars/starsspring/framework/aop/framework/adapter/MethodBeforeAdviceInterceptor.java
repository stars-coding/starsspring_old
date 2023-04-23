package com.stars.starsspring.framework.aop.framework.adapter;

import com.stars.starsspring.framework.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 方法前置通知拦截器——类
 * 用于在目标方法执行前执行给定的前置通知。
 * <p>
 * <p>
 * 属性字段：
 * advice
 * <p>
 * 重写方法：
 * invoke
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * MethodBeforeAdviceInterceptor
 * MethodBeforeAdviceInterceptor
 * getAdvice
 * setAdvice
 *
 * @author stars
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    // 方法前置通知对象
    private MethodBeforeAdvice advice;

    /**
     * 无参构造函数
     */
    public MethodBeforeAdviceInterceptor() {
    }

    /**
     * 有参构造函数（方法前置通知对象）
     *
     * @param advice 方法前置通知对象
     */
    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    /**
     * 调用（方法调用对象）
     *
     * @param methodInvocation 方法调用对象
     * @return 目标方法的执行结果
     * @throws Throwable 如果执行过程中发生异常，则抛出Throwable异常
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return methodInvocation.proceed();
    }

    public MethodBeforeAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodBeforeAdvice advice) {
        this.advice = advice;
    }
}
