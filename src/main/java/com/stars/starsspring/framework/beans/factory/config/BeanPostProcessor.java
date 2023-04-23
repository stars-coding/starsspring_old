package com.stars.starsspring.framework.beans.factory.config;

import com.stars.starsspring.framework.beans.BeansException;

/**
 * Bean扩展处理器——接口
 * 定义了在Bean对象执行初始化方法前后，执行特定操作的机制。
 * 允许在Bean对象初始化过程中对其进行定制化操作，如属性设置、代理对象创建等。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * postProcessBeforeInitialization
 * postProcessAfterInitialization
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface BeanPostProcessor {

    /**
     * 初始化之前扩展处理（Bean对象，Bean的名称）
     * 前置处理器
     * 在Bean对象执行初始化方法之前，执行此方法。
     *
     * @param bean     当前Bean对象
     * @param beanName 当前Bean的名称
     * @return 修改后的Bean对象，可以是原始对象或修改后的代理对象
     * @throws BeansException 如果在处理过程中发生异常，则抛出BeansException异常
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 初始化之后扩展处理（Bean对象，Bean的名称）
     * 后置处理器
     * 在Bean对象执行初始化方法之后，执行此方法。
     *
     * @param bean     当前Bean对象
     * @param beanName 当前Bean的名称
     * @return 修改后的Bean对象，可以是原始对象或修改后的代理对象
     * @throws BeansException 如果在处理过程中发生异常，则抛出BeansException异常
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
