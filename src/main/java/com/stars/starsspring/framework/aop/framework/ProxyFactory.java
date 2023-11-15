package com.stars.starsspring.framework.aop.framework;

import com.stars.starsspring.framework.aop.AdvisedSupport;

/**
 * 代理工厂——类
 * 根据配置信息选择合适的代理方式，然后创建代理对象。
 * <p>
 * <p>
 * 属性字段：
 * advisedSupport
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * ProxyFactory
 * getProxy
 * createAopProxy
 *
 * @author stars
 */
public class ProxyFactory {

    // 通知支持对象，封装了代理相关的配置和信息-
    private AdvisedSupport advisedSupport;

    /**
     * 有参构造函数（通知支持对象）
     * 用于配置代理相关信息。
     *
     * @param advisedSupport 通知支持对象
     */
    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    /**
     * 获取代理
     * 创建并返回代理对象。
     *
     * @return 代理对象
     */
    public Object getProxy() {
        return this.createAopProxy().getProxy();
    }

    /**
     * 创建AOP代理
     * 根据配置信息创建AOP代理对象，可以是基于CGLIB的代理或基于JDK动态代理的代理。
     *
     * @return AOP代理对象
     */
    private AopProxy createAopProxy() {
        // 根据配置选择使用CGLIB还是JDK动态代理
        if (this.advisedSupport.isProxyTargetClass()) {
            return new CglibAopProxy(this.advisedSupport);
        }
        return new JdkDynamicAopProxy(this.advisedSupport);
    }
}
