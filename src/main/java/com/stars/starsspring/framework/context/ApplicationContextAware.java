package com.stars.starsspring.framework.context;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.Aware;

/**
 * 应用上下文感知——接口
 * 用于让Bean对象感知其所在的应用上下文对象。
 * 当一个Bean对象实现了这个接口，容器在初始化该Bean对象时，会调用它的setApplicationContext方法，
 * 从而将其所在的应用上下文对象传递给它，使得该Bean对象可以在运行时与应用上下文对象进行交互。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * setApplicationContext
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 设置应用上下文（应用上下文对象）
     *
     * @param applicationContext 当前Bean对象所在的应用上下文对象
     * @throws BeansException 如果设置过程中出现异常，则抛出BeansException异常
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
