package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 简单实例化策略——类
 * JDK实例化，基于反射机制实例化Bean对象。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * instantiate
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    /**
     * 实例化（Bean定义对象，Bean的名称，构造函数对象，构造函数参数数）
     *
     * @param beanDefinition Bean定义对象
     * @param beanName       Bean的名称
     * @param ctor           构造函数对象，目的是为了拿到符合入参信息相对应的构造函数
     * @param args           构造函数参数
     * @return 实例化的Bean对象
     * @throws BeansException 如果实例化过程中出现异常，则抛出BeansException异常
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        Class clazz = beanDefinition.getBeanClass();
        try {
            if (ctor != null) {
                // 使用指定构造函数实例化对象
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                // 使用默认构造函数实例化对象
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
        }
    }
}
