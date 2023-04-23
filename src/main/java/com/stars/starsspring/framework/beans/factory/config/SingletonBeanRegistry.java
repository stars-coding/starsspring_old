package com.stars.starsspring.framework.beans.factory.config;

/**
 * 单例Bean注册——接口
 * 定义了一种机制，用于管理单例Bean对象的获取和注册。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getSingleton
 * registerSingleton
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface SingletonBeanRegistry {

    /**
     * 获取单例（Bean的名称）
     *
     * @param beanName Bean的名称
     * @return 单例Bean对象，如果不存在则返回null
     */
    Object getSingleton(String beanName);

    /**
     * 注册单例（Bean的名称，单例对象）
     *
     * @param beanName        Bean的名称
     * @param singletonObject 单例对象
     */
    void registerSingleton(String beanName, Object singletonObject);
}
