package com.stars.starsspring.framework.aop.framework;

/**
 * AOP代理——接口
 * 提供了一种统一的方式来获取代理对象，使得代理对象的创建对于应用程序代码来说是透明的。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getProxy
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface AopProxy {

    /**
     * 获取代理
     * 获取代理对象。
     *
     * @return 代理对象
     */
    Object getProxy();
}
