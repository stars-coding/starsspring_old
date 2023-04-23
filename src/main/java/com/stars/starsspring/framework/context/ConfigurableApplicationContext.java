package com.stars.starsspring.framework.context;

import com.stars.starsspring.framework.beans.BeansException;

/**
 * 配置应用上下文——接口
 * 允许应用程序上下文在初始化后进行刷新、注册关闭挂钩和关闭。
 * 允许应用程序有更多的控制权，以适应特定的需求。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * refresh
 * registerShutdownHook
 * close
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新
     * 刷新应用程序上下文，使其处于可用状态。
     *
     * @throws BeansException 如果刷新过程中发生错误，则抛出BeansException异常
     */
    void refresh() throws BeansException;

    /**
     * 注册关闭挂钩
     * 注册一个JVM关闭挂钩，以确保在JVM关闭时关闭应用程序上下文。
     */
    void registerShutdownHook();

    /**
     * 关闭
     * 关闭应用程序上下文，释放所有资源。
     */
    void close();
}
