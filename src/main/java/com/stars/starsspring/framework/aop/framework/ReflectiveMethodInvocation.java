package com.stars.starsspring.framework.aop.framework;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * 反射方法调用——类
 * 用于实现方法拦截器的逻辑，如前置增强、后置增强等。
 * 通常由AOP内部使用，应用开发者一般不需要直接使用它。
 * <p>
 * <p>
 * 属性字段：
 * target
 * method
 * arguments
 * <p>
 * 重写方法：
 * getMethod
 * getArguments
 * proceed
 * getThis
 * getStaticPart
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * ReflectiveMethodInvocation
 *
 * @author stars
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    // 目标对象
    protected final Object target;
    // 方法对象
    protected final Method method;
    // 传入参数
    protected final Object[] arguments;

    /**
     * 有参构造函数（目标对象，方法对象，传入参数）
     *
     * @param target    目标对象
     * @param method    方法对象
     * @param arguments 传入参数
     */
    public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    /**
     * 获取方法
     * 获取目标方法的方法对象。
     *
     * @return 方法对象
     */
    @Override
    public Method getMethod() {
        return method;
    }

    /**
     * 获取传入参数
     * 获取传入目标方法的参数数组。
     *
     * @return 参数数组
     */
    @Override
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * 继续
     * 执行目标方法，通过反射调用目标方法并传递参数，返回方法的执行结果。
     *
     * @return 方法的执行结果
     * @throws Throwable 如果目标方法抛出异常，则抛出Throwable异常
     */
    @Override
    public Object proceed() throws Throwable {
        return this.method.invoke(this.target, this.arguments);
    }

    /**
     * 获取这个对象
     * 获取目标对象，即拦截器链中的下一个拦截器或目标类的实例。
     *
     * @return 目标对象
     */
    @Override
    public Object getThis() {
        return this.target;
    }

    /**
     * 获取静态部分
     * 返回方法对象，表示要执行的目标方法。
     *
     * @return 方法对象
     */
    @Override
    public AccessibleObject getStaticPart() {
        return this.method;
    }
}
