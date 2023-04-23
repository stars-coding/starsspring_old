package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工厂Bean注册支持——抽象类
 * 用于缓存工厂Bean对象创建的对象。
 * 主要处理关于工厂Bean对象此类对象的注册操作。
 * <p>
 * <p>
 * 属性字段：
 * factoryBeanObjectCache
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * getCachedObjectForFactoryBean
 * getObjectFromFactoryBean
 * doGetObjectFromFactoryBean
 *
 * @author stars
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    // 工厂Bean对象缓存Map，用于缓存工厂Bean对象创建的对象的缓存，单例对象
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();

    /**
     * 获取缓存对象从工厂Bean（Bean的名称）
     * 从缓存中获取工厂Bean对象创建的对象实例。
     *
     * @param beanName Bean的名称
     * @return 已缓存的对象，如果没有则返回null
     */
    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != DefaultSingletonBeanRegistry.NULL_OBJECT ? object : null);
    }

    /**
     * 获取对象从工厂Bean（工厂Bean对象，Bean的名称）
     * 从工厂Bean对象中获取对象。
     *
     * @param factory  工厂Bean对象
     * @param beanName Bean的名称
     * @return 工厂Bean对象创建的对象
     */
    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        if (factory.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = this.doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, (object != null ? object : DefaultSingletonBeanRegistry.NULL_OBJECT));
            }
            return (object != DefaultSingletonBeanRegistry.NULL_OBJECT ? object : null);
        } else {
            return this.doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    /**
     * 执行获取对象从工厂Bean（工厂Bean对象，Bean的名称）
     * 从工厂Bean对象中获取对象。
     *
     * @param factory  工厂Bean对象
     * @param beanName Bean的名称
     * @return 工厂Bean对象创建的对象
     * @throws BeansException 如果获取对象过程中发生异常，则抛出BeansException异常
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw an exception while creating object[" + beanName + "]", e);
        }
    }
}
