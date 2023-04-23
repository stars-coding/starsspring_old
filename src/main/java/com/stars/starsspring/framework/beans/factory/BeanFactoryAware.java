package com.stars.starsspring.framework.beans.factory;

import com.stars.starsspring.framework.beans.BeansException;

/**
 * Bean工厂感知——接口
 * 用于将Bean工厂对象注入到实现类中。
 * 实现了该接口的Bean对象可以在初始化时获得对Bean工厂对象的引用，从而可以访问容器中的其他Bean对象。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * setBeanFactory
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface BeanFactoryAware extends Aware {

    /**
     * 设置Bean工厂（Bean工厂对象）
     *
     * @param beanFactory Bean工厂对象，用于访问容器中的其他Bean对象
     * @throws BeansException 如果设置过程中出现异常，则抛出BeansException异常
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
