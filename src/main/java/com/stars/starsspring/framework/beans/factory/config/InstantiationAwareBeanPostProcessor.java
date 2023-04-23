package com.stars.starsspring.framework.beans.factory.config;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.PropertyValues;

/**
 * 实例化感知Bean扩展处理器——接口
 * 用于在Bean对象实例化的不同阶段执行自定义的处理操作。
 * 它包含了在Bean对象实例化前后、以及属性注入前的回调方法。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * postProcessBeforeInstantiation
 * postProcessAfterInstantiation
 * postProcessPropertyValues
 * getEarlyBeanReference
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 实例化之前扩展处理（Bean的类对象，Bean的名称）
     * 在Bean对象执行实例化方法之前执行，可以返回一个代理对象来替代原始的Bean对象。
     *
     * @param beanClass 目标Bean的类对象
     * @param beanName  目标Bean的名称
     * @return 返回的对象可以是原始Bean对象或代理对象
     * @throws BeansException 如果处理过程中发生异常，则抛出BeansException异常
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * 实例化之后扩展处理（Bean对象，Bean的名称）
     * 在Bean对象执行实例化方法之后，属性注入之前执行。
     *
     * @param bean     目标Bean对象
     * @param beanName 目标Bean的名称
     * @return 返回true表示继续属性注入，返回false表示取消属性注入
     * @throws BeansException 如果处理过程中发生异常，则抛出BeansException异常
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * 扩展处理属性值集（属性值集对象，Bean对象，Bean的名称）
     * 在属性注入之前，对属性值进行后处理，例如根据一些条件检查属性的值是否合法。
     *
     * @param propertyValues 当前Bean的属性值集对象
     * @param bean           目标Bean对象
     * @param beanName       目标Bean的名称
     * @return 返回处理后的属性值集对象
     * @throws BeansException 如果处理过程中发生异常，则抛出BeansException异常
     */
    PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException;

    /**
     * 获取早期Bean引用（Bean对象，Bean的名称）
     * 获取早期引用的Bean对象。
     * 在Spring中，由SmartInstantiationAwareBeanPostProcessor对象的getEarlyBeanReference方法提供支持。
     * 通常情况下，直接返回原始的Bean对象。
     *
     * @param bean     目标Bean对象
     * @param beanName 目标Bean的名称
     * @return 返回的对象通常是原始Bean对象
     */
    default Object getEarlyBeanReference(Object bean, String beanName) {
        return bean;
    }
}
