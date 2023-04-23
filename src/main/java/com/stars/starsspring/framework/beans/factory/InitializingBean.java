package com.stars.starsspring.framework.beans.factory;

/**
 * 初始化Bean——接口
 * 初始化Bean的回调接口。
 * 开发者可以在此方法中进行自定义初始化操作。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * afterPropertiesSet
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface InitializingBean {

    /**
     * 属性填充后
     * 在属性填充后执行的初始化方法。
     *
     * @throws Exception 如果在初始化过程中发生异常，则抛出Exception异常
     */
    void afterPropertiesSet() throws Exception;
}
