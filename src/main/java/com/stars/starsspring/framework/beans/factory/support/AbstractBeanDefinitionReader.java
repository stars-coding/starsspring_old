package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.core.io.DefaultResourceLoader;
import com.stars.starsspring.framework.core.io.ResourceLoader;

/**
 * 抽象Bean定义读取器——抽象类
 * <p>
 * <p>
 * 属性字段：
 * registry
 * resourceLoader
 * <p>
 * 重写方法：
 * getRegistry
 * getResourceLoader
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * AbstractBeanDefinitionReader
 * AbstractBeanDefinitionReader
 *
 * @author stars
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    // Bean定义注册表对象，用于注册读取到的Bean定义对象。
    private final BeanDefinitionRegistry registry;

    // 资源加载器对象，用于加载Bean定义对象所需的资源。
    private ResourceLoader resourceLoader;

    /**
     * 有参构造函数（Bean定义注册表对象）
     * 使用默认的资源加载器对象。
     *
     * @param registry Bean定义注册表对象
     */
    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    /**
     * 有参构造函数（Bean定义注册表对象，资源加载器对象）
     * 指定Bean定义注册表对象和资源加载器对象。
     *
     * @param registry       Bean定义注册表对象
     * @param resourceLoader 资源加载器对象
     */
    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    /**
     * 获取Bean定义注册表
     *
     * @return Bean定义注册表对象
     */
    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    /**
     * 获取资源加载器
     *
     * @return 资源加载器对象
     */
    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
