package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实例化策略——接口
 * 用于定义如何实例化一个Bean对象的策略。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * instantiate
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface InstantiationStrategy {

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
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;
}
