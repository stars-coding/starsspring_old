package com.stars.starsspring.framework.context.support;

import com.stars.starsspring.framework.beans.BeansException;

/**
 * 类路径XML应用上下文——类
 * 基于类路径的XML配置应用上下文，用于从XML文件加载Bean定义对象并刷新应用上下文。
 * <p>
 * <p>
 * 属性字段：
 * configLocations
 * <p>
 * 重写方法：
 * getConfigLocations
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * ClassPathXmlApplicationContext
 * ClassPathXmlApplicationContext
 * ClassPathXmlApplicationContext
 *
 * @author stars
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    // 配置位置对象，存储配置文件的位置
    private String[] configLocations;

    /**
     * 无参构造函数
     * 不进行初始化。
     */
    public ClassPathXmlApplicationContext() {
    }

    /**
     * 有参构造函数（配置位置）
     * 从单个XML配置文件中加载Bean定义对象并刷新应用上下文。
     *
     * @param configLocation 单个XML配置文件的路径
     * @throws BeansException 如果在加载和刷新过程中发生异常，则抛出BeansException异常
     */
    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        this(new String[]{configLocation});
    }

    /**
     * 有参构造函数（配置位置数组）
     * 从多个XML配置文件中加载Bean定义对象并刷新应用上下文。
     *
     * @param configLocations 包含多个XML配置文件路径的数组
     * @throws BeansException 如果在加载和刷新过程中发生异常，则抛出BeansException异常
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        // 加载和刷新应用上下文
        this.refresh();
    }

    /**
     * 获取所有配置位置
     * 获取配置文件的位置数组，供上下文使用。
     *
     * @return 配置文件的位置数组
     */
    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
