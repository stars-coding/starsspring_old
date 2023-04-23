package com.stars.starsspring.framework.aop.framework;

import com.stars.starsspring.framework.aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态AOP代理——类
 * 用于生成基于JDK动态代理的代理对象，该代理对象会拦截指定的方法调用，执行方法拦截器。
 * 如果方法不符合匹配规则，则直接调用目标对象的方法。
 * <p>
 * <p>
 * 属性字段：
 * advised
 * <p>
 * 重写方法：
 * getProxy
 * invoke
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * JdkDynamicAopProxy
 *
 * @author stars
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    // 通知支持对象
    private final AdvisedSupport advised;

    /**
     * 有参构造函数（通知支持对象）
     *
     * @param advised 通知支持对象，包含了目标对象、方法拦截器等信息。
     */
    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    /**
     * 获取代理
     * 获取代理对象。
     *
     * @return 代理对象
     */
    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                this.advised.getTargetSource().getTargetClass(),
                this
        );
    }

    /**
     * 调用（代理对象，方法对象，传入参数）
     * 代理对象的方法调用处理，根据匹配规则执行拦截器或目标方法。
     *
     * @param proxy  代理对象
     * @param method 方法对象
     * @param args   方法参数
     * @return 方法执行结果
     * @throws Throwable 如果目标方法抛出异常，则抛出Throwable异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (this.advised.getMethodMatcher().matches(method, this.advised.getTargetSource().getTarget().getClass())) {
            MethodInterceptor methodInterceptor = this.advised.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(this.advised.getTargetSource().getTarget(), method, args));
        }
        return method.invoke(this.advised.getTargetSource().getTarget(), args);
    }
}
