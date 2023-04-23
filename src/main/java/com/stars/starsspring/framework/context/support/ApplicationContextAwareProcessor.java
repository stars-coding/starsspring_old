package com.stars.starsspring.framework.context.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.config.BeanPostProcessor;
import com.stars.starsspring.framework.context.ApplicationContext;
import com.stars.starsspring.framework.context.ApplicationContextAware;

/**
 * 应用上下文感知处理器——类
 * 是一个Bean对象后置处理器，用于在Bean对象初始化之前回调设置应用上下文对象。
 * 在容器初始化Bean对象时被调用，用于处理实现了ApplicationContextAware接口的Bean对象。
 * <p>
 * <p>
 * 属性字段：
 * applicationContext
 * <p>
 * 重写方法：
 * postProcessBeforeInitialization
 * postProcessAfterInitialization
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * ApplicationContextAwareProcessor
 *
 * @author stars
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    // 应用上下文对象
    private final ApplicationContext applicationContext;

    /**
     * 有参构造函数（应用上下文对象）
     *
     * @param applicationContext 当前容器的应用上下文对象
     */
    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

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
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            // 调用实现了ApplicationContextAware接口的Bean对象的setApplicationContext方法，将当前的应用上下文对象传入
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

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
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
