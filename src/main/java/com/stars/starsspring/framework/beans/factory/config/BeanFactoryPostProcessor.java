package com.stars.starsspring.framework.beans.factory.config;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.ConfigurableListableBeanFactory;

/**
 * Bean工厂扩展处理器——接口
 * 这个接口允许在容器中的Bean定义对象加载完成之后，但在实例化Bean对象之前对其属性进行修改。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * postProcessBeanFactory
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface BeanFactoryPostProcessor {

    /**
     * 扩展处理Bean工厂（配置列表Bean工厂对象）
     * 在所有的Bean定义对象加载完成后，实例化Bean对象之前，提供修改Bean定义对象属性的机制。
     *
     * @param beanFactory 可配置的Bean工厂对象，包含了已加载的Bean定义对象
     * @throws BeansException 如果在处理过程中发生异常，则抛出BeansException异常
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
