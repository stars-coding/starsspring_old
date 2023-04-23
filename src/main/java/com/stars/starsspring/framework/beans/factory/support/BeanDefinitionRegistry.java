package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;

/**
 * Bean定义注册表——接口
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * registerBeanDefinition
 * getBeanDefinition
 * containsBeanDefinition
 * getBeanDefinitionNames
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface BeanDefinitionRegistry {

    /**
     * 注册Bean定义（Bean的名称，Bean定义对象）
     *
     * @param beanName       Bean的名称
     * @param beanDefinition Bean定义对象
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 获取Bean定义（Bean的名称）
     *
     * @param beanName Bean的名称
     * @return 对应的Bean定义对象
     * @throws BeansException 如果查询过程中出现异常，则抛出BeansException异常
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 是否包含Bean定义（Bean的名称）
     *
     * @param beanName Bean的名称
     * @return 如果包含对应名称的Bean定义对象，则返回true，否则返回false
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取所有Bean定义对象的名称
     *
     * @return 所有已Bean定义对象的名称数组
     */
    String[] getBeanDefinitionNames();
}
