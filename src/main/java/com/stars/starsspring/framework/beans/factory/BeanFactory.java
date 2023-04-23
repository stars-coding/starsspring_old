package com.stars.starsspring.framework.beans.factory;

import com.stars.starsspring.framework.beans.BeansException;

/**
 * Bean工厂——接口
 * 定义了一种机制，用于管理Bean对象的获取。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getBean
 * getBean
 * getBean
 * getBean
 * containsBean
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface BeanFactory {

    /**
     * 获取Bean（Bean的名称）
     *
     * @param name Bean的名称
     * @return 匹配名称的Bean对象
     * @throws BeansException 如果无法找到或创建Bean对象，则抛出BeansException异常
     */
    Object getBean(String name) throws BeansException;

    /**
     * 获取Bean（Bean的名称，构造参数）
     *
     * @param name Bean的名称
     * @param args 构造参数
     * @return 匹配名称的Bean对象
     * @throws BeansException 如果无法找到或创建Bean对象，则抛出BeansException异常
     */
    Object getBean(String name, Object... args) throws BeansException;

    /**
     * 获取Bean（Bean的名称，Bean的类对象）
     *
     * @param name         Bean的名称
     * @param requiredType 要求的Bean类型
     * @return 匹配名称和类型的Bean对象
     * @throws BeansException 如果无法找到或创建Bean对象，则抛出BeansException异常
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    /**
     * 获取Bean（Bean的类对象）
     *
     * @param requiredType 要求的Bean类型
     * @return 匹配类型的Bean对象
     * @throws BeansException 如果无法找到或创建Bean对象，则抛出BeansException异常
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * 是否包含Bean（Bean的名称）
     *
     * @param name 要检查的Bean名称
     * @return 如果存在具有给定名称的Bean对象，则返回true；否则返回false
     */
    boolean containsBean(String name);
}
