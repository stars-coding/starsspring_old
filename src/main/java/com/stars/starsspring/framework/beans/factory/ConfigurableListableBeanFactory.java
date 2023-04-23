package com.stars.starsspring.framework.beans.factory;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.config.AutowireCapableBeanFactory;
import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;
import com.stars.starsspring.framework.beans.factory.config.ConfigurableBeanFactory;

/**
 * 配置列表Bean工厂——接口
 * 提供了配置和管理Bean定义对象的功能，并且能够列举Bean对象的信息。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getBeanDefinition
 * preInstantiateSingletons
 * <p>
 * 编写方法：
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 获取Bean定义（Bean的名称）
     * 根据Bean名称获取对应的Bean定义。
     *
     * @param beanName Bean的名称
     * @return Bean定义对象
     * @throws BeansException 如果无法找到或解析Bean定义对象，将抛出BeansException异常
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 预实例化所有单例
     * 预实例化所有的单例Bean对象。
     * 这将初始化配置中定义的所有单例Bean对象，使它们准备好在需要时使用。
     *
     * @throws BeansException 如果在预实例化过程中发生任何异常，将抛出BeansException异常
     */
    void preInstantiateSingletons() throws BeansException;
}
