package com.stars.starsspring.framework.beans.factory;

/**
 * 工厂Bean——接口
 * 用于创建特定类型的对象。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getObject
 * getObjectType
 * isSingleton
 * <p>
 * 编写方法：
 *
 * @param <T> 要创建的对象的类型
 * @author stars
 */
public interface FactoryBean<T> {

    /**
     * 获取对象
     * 获取工厂Bean对象创建的对象。
     *
     * @return 创建的对象
     * @throws Exception 如果创建对象过程中发生异常，则抛出Exception异常
     */
    T getObject() throws Exception;

    /**
     * 获取对象类型
     * 获取创建的对象的类型。
     *
     * @return 对象的类对象
     */
    Class<?> getObjectType();

    /**
     * 是否单例
     * 判断创建的对象是否是单例。
     *
     * @return 如果是单例对象，返回true；否则返回false
     */
    boolean isSingleton();
}
