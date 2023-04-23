package com.stars.starsspring.framework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * 通知支持——类
 * 通常与切面对象相关联，用于配置如何对目标类进行代理以应用横切关注点。
 * <p>
 * <p>
 * 属性字段：
 * proxyTargetClass
 * targetSource
 * methodInterceptor
 * methodMatcher
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * isProxyTargetClass
 * setProxyTargetClass
 * getTargetSource
 * setTargetSource
 * getMethodInterceptor
 * setMethodInterceptor
 * getMethodMatcher
 * setMethodMatcher
 *
 * @author stars
 */
public class AdvisedSupport {

    // 代理目标类对象
    private boolean proxyTargetClass = false;
    // 目标源对象
    private TargetSource targetSource;
    // 方法拦截器对象
    private MethodInterceptor methodInterceptor;
    // 方法匹配器对象
    private MethodMatcher methodMatcher;

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
