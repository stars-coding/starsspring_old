package com.stars.starsspring.framework.aop.framework;

import com.stars.starsspring.framework.aop.AdvisedSupport;
import com.stars.starsspring.framework.util.ClassUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIBAOP代理——类
 * 用于生成基于 CGLIB 动态代理的代理对象，该代理对象会拦截指定的方法调用，执行方法拦截器。
 * 如果方法不符合匹配规则，则直接调用目标对象的方法。
 * <p>
 * <p>
 * 属性字段：
 * advised
 * <p>
 * 重写方法：
 * getProxy
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * CglibAopProxy
 *
 * @author stars
 */
public class CglibAopProxy implements AopProxy {

    // 通知支持对象
    private final AdvisedSupport advised;

    /**
     * 有参构造函数（通知支持对象）
     *
     * @param advised 通知支持对象
     */
    public CglibAopProxy(AdvisedSupport advised) {
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
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ClassUtils.getActualClass(this.advised.getTargetSource().getTarget().getClass()));
        enhancer.setInterfaces(this.advised.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(this.advised));
        return enhancer.create();
    }

    /**
     * 动态通知拦截器——内部类
     * <p>
     * <p>
     * 属性字段：
     * advised
     * <p>
     * 重写方法：
     * intercept
     * <p>
     * 定义方法：
     * <p>
     * 编写方法：
     * DynamicAdvisedInterceptor
     *
     * @author stars
     */
    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

        // 通知支持对象
        private final AdvisedSupport advised;

        /**
         * 有参构造函数（通知支持对象）
         *
         * @param advised 通知支持对象
         */
        public DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        /**
         * 拦截（对象，方法对象，传入参数，方法代理对象）
         * 根据匹配规则执行方法拦截器或直接调用目标方法。
         *
         * @param o           代理对象
         * @param method      方法对象
         * @param objects     方法参数
         * @param methodProxy CGLIB提供的方法代理对象
         * @return 方法执行结果
         * @throws Throwable 如果目标方法抛出异常，则抛出Throwable异常
         */
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(this.advised.getTargetSource().getTarget(), method, objects, methodProxy);
            if (this.advised.getMethodMatcher().matches(method, this.advised.getTargetSource().getTarget().getClass())) {
                return this.advised.getMethodInterceptor().invoke(methodInvocation);
            }
            return methodInvocation.proceed();
        }
    }

    /**
     * CGLIB方法调用——内部类
     * <p>
     * <p>
     * 属性字段：
     * methodProxy
     * <p>
     * 重写方法：
     * proceed
     * <p>
     * 定义方法：
     * <p>
     * 编写方法：
     * CglibMethodInvocation
     *
     * @author stars
     */
    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        // 方法代理对象
        private final MethodProxy methodProxy;

        /**
         * 有参构造函数（目标对象，方法对象，传入参数，方法代理对象）
         *
         * @param target      目标对象
         * @param method      方法对象
         * @param arguments   方法参数
         * @param methodProxy CGLIB提供的方法代理对象
         */
        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
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
            return this.methodProxy.invoke(this.target, this.arguments);
        }
    }
}
