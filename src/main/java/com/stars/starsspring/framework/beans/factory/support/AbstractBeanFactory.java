package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.FactoryBean;
import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;
import com.stars.starsspring.framework.beans.factory.config.BeanPostProcessor;
import com.stars.starsspring.framework.beans.factory.config.ConfigurableBeanFactory;
import com.stars.starsspring.framework.core.convert.ConversionService;
import com.stars.starsspring.framework.util.ClassUtils;
import com.stars.starsspring.framework.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象Bean工厂——抽象类
 * 提供了一些基本的Bean对象获取和创建的方法，并且包含了一些用于处理Bean扩展处理器对象、String值解析和类型转换服务的成员变量和方法。
 * <p>
 * <p>
 * 属性字段：
 * beanPostProcessors
 * embeddedValueResolvers
 * beanClassLoader
 * conversionService
 * <p>
 * 重写方法：
 * getBean
 * getBean
 * getBean
 * containsBean
 * addBeanPostProcessor
 * addEmbeddedValueResolver
 * resolveEmbeddedValue
 * setConversionService
 * ConversionService
 * <p>
 * 定义方法：
 * containsBeanDefinition
 * getBeanDefinition
 * createBean
 * <p>
 * 编写方法：
 * doGetBean
 * getObjectForBeanInstance
 * getBeanPostProcessors
 * getBeanClassLoader
 *
 * @author stars
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    // Bean扩展处理列表对象
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    // 嵌入式值解析器列表对象
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    // Bean类加载器对象
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    // 转换服务对象
    private ConversionService conversionService;

    /**
     * 获取Bean（Bean的名称）
     *
     * @param name Bean的名称
     * @return 匹配名称的Bean对象
     * @throws BeansException 如果无法找到或创建Bean对象，则抛出BeansException异常
     */
    @Override
    public Object getBean(String name) throws BeansException {
        return this.doGetBean(name, null);
    }

    /**
     * 获取Bean（Bean的名称，构造参数）
     *
     * @param name Bean的名称
     * @param args 构造参数
     * @return 匹配名称的Bean对象
     * @throws BeansException 如果无法找到或创建Bean对象，则抛出BeansException异常
     */
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return this.doGetBean(name, args);
    }

    /**
     * 获取Bean（Bean的名称，Bean的类对象）
     *
     * @param name         Bean的名称
     * @param requiredType 要求的Bean类型
     * @return 匹配名称和类型的Bean对象
     * @throws BeansException 如果无法找到或创建Bean对象，则抛出BeansException异常
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) this.getBean(name);
    }

    /**
     * 是否包含Bean（Bean的名称）
     *
     * @param name 要检查的Bean名称
     * @return 如果存在具有给定名称的Bean对象，则返回true；否则返回false
     */
    @Override
    public boolean containsBean(String name) {
        return this.containsBeanDefinition(name);
    }

    /**
     * 是否包含Bean定义（Bean的名称）
     *
     * @param beanName 要检查的Bean名称
     * @return 如果存在具有给定名称的Bean定义对象，则返回true；否则返回false
     */
    protected abstract boolean containsBeanDefinition(String beanName);

    /**
     * 执行获取Bean（Bean的名称，构造参数）
     * 从容器中获取指定名称的Bean对象。
     *
     * @param name Bean的名称
     * @param args 创建Bean对象时传递的参数
     * @return Bean对象
     */
    @SuppressWarnings("unchecked")
    protected <T> T doGetBean(final String name, final Object[] args) {
        // 首先尝试从单例缓存中获取Bean对象
        Object sharedInstance = this.getSingleton(name);
        if (sharedInstance != null) {
            // 如果单例缓存中存在，则需要根据Bean对象的类型进行处理
            // 如果是工厂Bean对象，则需要调用工厂Bean对象的getObject方法获取实际对象
            return (T) this.getObjectForBeanInstance(sharedInstance, name);
        }
        // 如果单例缓存中没有对应的Bean对象，则需要创建Bean对象
        // 首先获取Bean定义对象
        BeanDefinition beanDefinition = this.getBeanDefinition(name);
        // 调用createBean方法创建Bean对象
        Object bean = this.createBean(name, beanDefinition, args);
        // 返回Bean对象，如果需要，会根据工厂Bean对象进行处理
        return (T) this.getObjectForBeanInstance(bean, name);
    }

    /**
     * 获取对象从Bean实例
     * 根据Bean实例获取实际对象。
     *
     * @param beanInstance Bean实例
     * @param beanName     Bean的名称
     * @return 实际对象
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        // 如果beanInstance不是FactoryBean类型，直接返回实例
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        // 否则尝试从缓存中获取实际对象
        Object object = this.getCachedObjectForFactoryBean(beanName);
        // 如果缓存中没有实际对象，则需要调用工厂Bean对象的getObject方法获取实际对象
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = this.getObjectFromFactoryBean(factoryBean, beanName);
        }
        // 返回获取到的实际对象
        return object;
    }

    /**
     * 获取Bean定义（Bean的名称）
     *
     * @param beanName Bean的名称
     * @return Bean定义对象，包含了Bean的元数据信息
     * @throws BeansException 如果获取Bean定义过程中出现异常，则抛出BeansException异常
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 创建Bean（Bean的名称，Bean定义对象，构造参数）
     *
     * @param beanName       Bean的名称
     * @param beanDefinition Bean定义对象
     * @param args           用于构造Bean实例的参数
     * @return 创建的Bean对象
     * @throws BeansException 如果创建过程中出现异常，则抛出BeansException异常
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    /**
     * 添加Bean扩展处理器（Bean扩展处理器对象）
     * 向Bean工厂对象中添加一个Bean扩展处理器对象。
     *
     * @param beanPostProcessor 要添加的Bean扩展处理器对象
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 添加嵌入式值解析器（字符串值解析对象）
     * 添加一个字符串值解析器，用于解析Bean定义对象中的字符串嵌入式值，例如注解属性。
     *
     * @param valueResolver 要添加的值解析器
     * @since 3.0
     */
    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    /**
     * 解析嵌入式值（嵌入式值）
     * 解析给定的嵌入式值，例如注解属性。这个方法会将嵌入式值替换为实际的值。
     *
     * @param value 要解析的嵌入式值
     * @return 解析后的值（可能是原始值）
     * @since 3.0
     */
    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    /**
     * 设置转换服务（转换服务对象）
     * 用于转换属性值。这是一个替代JavaBeans的PropertyEditors的选择。
     *
     * @param conversionService 要设置的转换服务
     * @since 3.0
     */
    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * 获取转换服务
     * 返回与该工厂关联的转换服务。
     *
     * @return 关联的转换服务对象，如果没有则返回null
     * @since 3.0
     */
    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }
}
