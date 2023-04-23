package com.stars.starsspring.framework.beans.factory.config;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.BeanFactory;

/**
 * 自动胜任Bean工厂——接口
 * 在Bean初始化前后进行处理。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * applyBeanPostProcessorsBeforeInitialization
 * applyBeanPostProcessorsAfterInitialization
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 前置处理器（已存在的Bean，Bean的名称）
     * 在Bean初始化之前调用BeanPostProcessors接口实现类的postProcessBeforeInitialization方法。
     *
     * @param existingBean 已存在的Bean实例
     * @param beanName     Bean的名称
     * @return 初始化前的Bean实例
     * @throws BeansException 如果在初始化前执行过程中发生异常，则抛出BeansException异常
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 后置处理器（已存在的Bean，Bean的名称）
     * 在Bean初始化之后调用BeanPostProcessors接口实现类的postProcessorsAfterInitialization方法。
     *
     * @param existingBean 已存在的Bean实例
     * @param beanName     Bean的名称
     * @return 初始化后的Bean实例
     * @throws BeansException 如果在初始化后执行过程中发生异常，则抛出BeansException异常
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
