package com.stars.starsspring.framework.context.support;

import com.stars.starsspring.framework.beans.factory.support.DefaultListableBeanFactory;
import com.stars.starsspring.framework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 抽象XML应用上下文——类
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * loadBeanDefinitions
 * <p>
 * 定义方法：
 * getConfigLocations
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    /**
     * 加载Bean定义（Bean工厂对象）
     * 加载Bean定义对象到Bean工厂对象中。
     *
     * @param beanFactory 要加载Bean定义对象的Bean工厂对象
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        // 获取配置文件位置
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            // 加载Bean定义对象
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 获取配置位置
     * 获取配置文件的位置。
     *
     * @return 返回配置文件的位置数组
     */
    protected abstract String[] getConfigLocations();
}
