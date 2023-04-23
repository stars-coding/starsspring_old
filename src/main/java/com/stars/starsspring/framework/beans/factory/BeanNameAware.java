package com.stars.starsspring.framework.beans.factory;

/**
 * Bean名称感知——接口
 * 用于让Bean对象感知自己在容器中的Bean的名称。
 * 当一个Bean对象实现了这个接口，容器在初始化该Bean对象时，会调用它的setBeanName方法，
 * 从而将该Bean对象在容器中的名称传递给它，使得该Bean对象可以在运行时获取到自己在容器中的唯一标识。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * setBeanName
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface BeanNameAware extends Aware {

    /**
     * 设置Bean名称（Bean的名称）
     *
     * @param name Bean对象在容器中的名称
     */
    void setBeanName(String name);
}
