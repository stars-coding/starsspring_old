package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.core.io.Resource;
import com.stars.starsspring.framework.core.io.ResourceLoader;

/**
 * Bean定义读取器——接口
 * 用于从资源中读取和加载Bean定义对象。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getRegistry
 * getResourceLoader
 * loadBeanDefinitions
 * loadBeanDefinitions
 * loadBeanDefinitions
 * loadBeanDefinitions
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface BeanDefinitionReader {

    /**
     * 获取注册表
     *
     * @return Bean定义注册表对象
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源加载器
     *
     * @return 资源加载器对象
     */
    ResourceLoader getResourceLoader();

    /**
     * 加载Bean定义（资源对象）
     * 从单个资源中加载Bean定义对象。
     *
     * @param resource 资源对象
     * @throws BeansException 如果加载过程中发生错误，则抛出BeansException异常
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;

    /**
     * 加载Bean定义（资源数组）
     * 从多个资源中加载Bean定义对象。
     *
     * @param resources 资源数组
     * @throws BeansException 如果加载过程中发生错误，则抛出BeansException异常
     */
    void loadBeanDefinitions(Resource... resources) throws BeansException;

    /**
     * 加载Bean定义（资源位置）
     * 从单个指定位置的资源中加载Bean定义对象。
     *
     * @param location 资源位置
     * @throws BeansException 如果加载过程中发生错误，则抛出BeansException异常
     */
    void loadBeanDefinitions(String location) throws BeansException;

    /**
     * 加载Bean定义（资源位置数组）
     * 从多个指定位置的资源中加载Bean定义对象。
     *
     * @param locations 资源位置数组
     * @throws BeansException 如果加载过程中发生错误，则抛出BeansException异常
     */
    void loadBeanDefinitions(String... locations) throws BeansException;
}
