package com.stars.starsspring.framework.context.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.stars.starsspring.framework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 抽象刷新应用上下文——类
 * <p>
 * <p>
 * 属性字段：
 * beanFactory
 * <p>
 * 重写方法：
 * refreshBeanFactory
 * getBeanFactory
 * <p>
 * 定义方法：
 * loadBeanDefinitions
 * <p>
 * 编写方法：
 * createBeanFactory
 *
 * @author stars
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    // Bean工厂对象
    private DefaultListableBeanFactory beanFactory;

    /**
     * 刷新Bean工厂
     * 用于创建Bean工厂对象并加载Bean定义对象。
     *
     * @throws BeansException 如果刷新Bean工厂对象过程中出现异常，则抛出BeansException异常
     */
    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = this.createBeanFactory();
        // 加载Bean定义对象
        this.loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 创建Bean工厂
     * 创建一个新的Bean工厂对象，默认使用默认列表Bean工厂对象。
     *
     * @return 返回新创建的Bean工厂对象
     */
    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载Bean定义（Bean工厂对象）
     * 加载Bean定义对象到Bean工厂对象中。
     *
     * @param beanFactory 要加载Bean定义对象的Bean工厂对象
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    /**
     * 获取Bean工厂
     * 获取当前应用上下文使用的Bean工厂对象。
     *
     * @return 返回当前应用上下文使用的Bean工厂对象
     */
    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }
}
