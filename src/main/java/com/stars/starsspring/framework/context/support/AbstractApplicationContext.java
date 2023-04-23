package com.stars.starsspring.framework.context.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.stars.starsspring.framework.beans.factory.config.BeanFactoryPostProcessor;
import com.stars.starsspring.framework.beans.factory.config.BeanPostProcessor;
import com.stars.starsspring.framework.context.ApplicationEvent;
import com.stars.starsspring.framework.context.ApplicationListener;
import com.stars.starsspring.framework.context.ConfigurableApplicationContext;
import com.stars.starsspring.framework.context.event.ApplicationEventMulticaster;
import com.stars.starsspring.framework.context.event.ContextClosedEvent;
import com.stars.starsspring.framework.context.event.ContextRefreshedEvent;
import com.stars.starsspring.framework.context.event.SimpleApplicationEventMulticaster;
import com.stars.starsspring.framework.core.convert.ConversionService;
import com.stars.starsspring.framework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象应用上下文——抽象类
 * <p>
 * <p>
 * 属性字段：
 * APPLICATION_EVENT_MULTICASTER_BEAN_NAME
 * applicationEventMulticaster
 * <p>
 * 重写方法：
 * refresh
 * publishEvent
 * getBeansOfType
 * getBeanDefinitionNames
 * getBean
 * getBean
 * getBean
 * getBean
 * containsBean
 * registerShutdownHook
 * close
 * <p>
 * 定义方法：
 * refreshBeanFactory
 * getBeanFactory
 * <p>
 * 编写方法：
 * finishBeanFactoryInitialization
 * invokeBeanFactoryPostProcessors
 * registerBeanPostProcessors
 * initApplicationEventMulticaster
 * registerListeners
 * finishRefresh
 *
 * @author stars
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    // 应用事件广播Bean名称
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    // 应用事件广播器对象
    private ApplicationEventMulticaster applicationEventMulticaster;

    /**
     * 刷新
     * 刷新应用程序上下文，使其处于可用状态。
     *
     * @throws BeansException 如果刷新过程中发生错误，则抛出BeansException异常
     */
    @Override
    public void refresh() throws BeansException {
        // 1. 创建BeanFactory对象，并加载BeanDefinition对象
        this.refreshBeanFactory();
        // 2. 获取BeanFactory对象
        ConfigurableListableBeanFactory beanFactory = this.getBeanFactory();
        // 3. 添加ApplicationContextAwareProcessor对象，让继承自ApplicationContextAware接口的Bean对象都能感知所属的ApplicationContext对象
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        // 4. 在Bean对象实例化之前，执行BeanFactoryPostProcessor对象
        this.invokeBeanFactoryPostProcessors(beanFactory);
        // 5. BeanPostProcessor对象需要提前于其他Bean对象实例化之前执行注册操作
        this.registerBeanPostProcessors(beanFactory);
        // 6. 初始化事件发布者
        this.initApplicationEventMulticaster();
        // 7. 注册事件监听器
        this.registerListeners();
        // 8. 设置类型转换器、提前实例化单例Bean对象
        this.finishBeanFactoryInitialization(beanFactory);
        // 9. 发布容器刷新完成事件
        this.finishRefresh();
    }

    /**
     * 完成Bean工厂初始化（配置列表Bean工厂对象）
     * 设置类型转换器、提前实例化单例Bean对象
     *
     * @param beanFactory 要初始化的Bean工厂对象
     */
    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        // 设置类型转换器
        if (beanFactory.containsBean("conversionService")) {
            Object conversionService = beanFactory.getBean("conversionService");
            if (conversionService instanceof ConversionService) {
                beanFactory.setConversionService((ConversionService) conversionService);
            }
        }
        // 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 刷新Bean工厂
     * 用于创建Bean工厂对象并加载Bean定义对象。
     *
     * @throws BeansException 如果刷新Bean工厂对象过程中出现异常，则抛出BeansException异常
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 获取Bean工厂
     * 获取配置好的配置列表Bean工厂对象。
     *
     * @return 配置好的配置列表Bean工厂对象
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 调用Bean工厂扩展处理器（配置列表Bean工厂对象）
     * 调用所有注册到容器中的BeanFactoryPostProcessor接口的实现类的postProcessBeanFactory方法。
     *
     * @param beanFactory Bean工厂对象，用于获取注册的Bean工厂扩展处理器对象
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        // 从BeanFactory对象中获取所有实现了BeanFactoryPostProcessor接口的Bean对象
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        // 遍历所有的BeanFactoryPostProcessor对象，并调用其postProcessBeanFactory方法
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册Bean扩展处理器（配置列表Bean工厂对象）
     * 注册容器中找到的所有BeanPostProcessor接口的实现类。
     *
     * @param beanFactory Bean工厂对象，用于获取注册的Bean扩展处理器对象
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        // 从BeanFactory对象中获取所有实现了BeanPostProcessor接口的Bean对象
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        // 遍历所有的BeanPostProcessor对象，并将它们注册到BeanFactory对象中
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * 初始化应用事件广播器
     */
    private void initApplicationEventMulticaster() {
        // 获取当前的BeanFactory对象
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        // 创建一个SimpleApplicationEventMulticaster对象，它是ApplicationEventMulticaster对象的默认实现
        this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        // 将ApplicationEventMulticaster对象注册为一个单例Bean对象，以便其他组件可以通过BeanFactory对象获取它
        beanFactory.registerSingleton(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
    }

    /**
     * 注册监听器
     */
    private void registerListeners() {
        // 获取所有已注册的ApplicationListener对象
        Collection<ApplicationListener> applicationListeners = this.getBeansOfType(ApplicationListener.class).values();
        // 逐个将监听器注册到事件多播器ApplicationEventMulticaster对象中
        for (ApplicationListener listener : applicationListeners) {
            this.applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 完成刷新
     */
    private void finishRefresh() {
        // 创建并发布ContextRefreshedEvent对象，通知容器已经刷新完成
        this.publishEvent(new ContextRefreshedEvent(this));
    }

    /**
     * 发布事件（应用事件对象）
     * 通知所有已注册到此应用程序的监听器有一个应用程序事件。
     *
     * @param event 要发布的事件对象
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        this.applicationEventMulticaster.multicastEvent(event);
    }

    /**
     * 获取Bean依据类型（Bean的类对象）
     * 按照给定的类型返回所有符合条件的Bean对象。
     *
     * @param type Bean的类对象
     * @param <T>  Bean的类型
     * @return 包含所有符合条件的Bean对象的Map，其中键是Bean的名称，值是Bean对象
     * @throws BeansException 如果发生任何Bean相关的异常
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return this.getBeanFactory().getBeansOfType(type);
    }

    /**
     * 获取所有Bean定义名称
     * 返回该工厂中所有Bean定义对象的名称。
     *
     * @return 包含所有Bean定义对象名称的数组
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return this.getBeanFactory().getBeanDefinitionNames();
    }

    /**
     * 获取Bean（Bean的名称）
     *
     * @param name Bean的名称
     * @return 匹配名称的Bean对象
     * @throws BeansException 如果无法找到或创建Bean对象，则抛出BeansException异常
     */
    @Override
    public Object getBean(String name) throws BeansException {
        return this.getBeanFactory().getBean(name);
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
        return this.getBeanFactory().getBean(name, args);
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
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return this.getBeanFactory().getBean(name, requiredType);
    }

    /**
     * 获取Bean（Bean的类对象）
     *
     * @param requiredType 要求的Bean类型
     * @return 匹配类型的Bean对象
     * @throws BeansException 如果无法找到或创建Bean对象，则抛出BeansException异常
     */
    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return this.getBeanFactory().getBean(requiredType);
    }

    /**
     * 是否包含Bean（Bean的名称）
     *
     * @param name 要检查的Bean名称
     * @return 如果存在具有给定名称的Bean对象，则返回true；否则返回false
     */
    @Override
    public boolean containsBean(String name) {
        return this.getBeanFactory().containsBean(name);
    }

    /**
     * 注册关闭挂钩
     * 注册一个JVM关闭挂钩，以确保在JVM关闭时关闭应用程序上下文。
     */
    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    /**
     * 关闭
     * 关闭应用程序上下文，释放所有资源。
     */
    @Override
    public void close() {
        // 发布容器关闭事件
        this.publishEvent(new ContextClosedEvent(this));
        // 执行销毁单例bean对象的销毁方法
        this.getBeanFactory().destroySingletons();
    }
}
